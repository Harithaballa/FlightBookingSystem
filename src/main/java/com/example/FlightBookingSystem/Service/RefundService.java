package com.example.FlightBookingSystem.Service;

import com.example.FlightBookingSystem.Dto.RefundRequest;
import com.example.FlightBookingSystem.Model.Booking;
import com.example.FlightBookingSystem.Model.Refund;
import com.example.FlightBookingSystem.Model.RefundStatus;
import com.example.FlightBookingSystem.Repository.RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefundService {

    BookingService bookingService;

    RefundRepository refundRepository;

    StripeService stripeService;

    PaymentService paymentService;

    @Autowired
    public RefundService(BookingService bookingService, RefundRepository refundRepository,StripeService stripeService,PaymentService paymentService){
        this.bookingService = bookingService;
        this.refundRepository = refundRepository;
        this.stripeService = stripeService;
        this.paymentService = paymentService;
    }

    public double calculate(RefundRequest request) throws Exception {
        Booking booking = bookingService.findById(request.getBookingId());
        long hoursBeforeDeparture = java.time.Duration.between(
                java.time.LocalDateTime.now(), booking.getTrip().getDepartureTime()).toHours();

        //make it dynamic
        if (hoursBeforeDeparture > (4*24)) {
            return booking.getAmount(); // Full refund
        } else if (hoursBeforeDeparture > (2*24)) {
            return booking.getAmount() * 0.75; // 75% refund
        } else if (hoursBeforeDeparture > 24) {
            return booking.getAmount() * 0.50; // 50% refund
        } else {
            return 0; // Non-refundable
        }
    }

    public double initiate(long bookingId) throws Exception {
        double refundAmount = calculate(new RefundRequest(bookingId));

        Refund refund = new Refund();
        refund.setAmount(refundAmount);
        refund.setBookingId(bookingId);
        refund.setStatus(RefundStatus.INITIATED);

        refundRepository.save(refund);

        return refundAmount;
    }

    public void process(Long id, String idempotencyKey) throws Exception {
        Refund refund = refundRepository.findById(id).orElseThrow(()-> new Exception("refund is not found for id: "+id));

        String paymentIntentId = paymentService.getPaymentIntentByBooking(refund.getBookingId());

        com.stripe.model.Refund stripeRefund = stripeService.refund(paymentIntentId,refund.getAmount(),idempotencyKey);

        String status = stripeRefund.getStatus();
        RefundStatus refundStatus = refund.getStatus();
        if (status.equals("failed")) {
            String failureReason = stripeRefund.getFailureReason();
            refundStatus = RefundStatus.FAILED;
            refund.setStatus(refundStatus);
            refundRepository.save(refund);
            throw new Exception("Refund failed: "+failureReason);
        }
        else if(status.equals("succeeded")){
            refundStatus = RefundStatus.COMPLETED;
        }
        else if(status.equals("pending")){
            refundStatus = RefundStatus.INPROGRESS;
        }

        refund.setStatus(refundStatus);
        refundRepository.save(refund);
    }

    public Refund findById(Long id) throws Exception {
        return refundRepository.findById(id).orElseThrow(()-> new Exception("refund is not found for id: "+id));
    }
}
