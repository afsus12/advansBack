package com.example.finalbackendadvans.dto;

import java.time.LocalDate;

public class RepaymentMilestoneDTO {


        private Long id;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public LocalDate getDueDate() {
                return dueDate;
        }

        public void setDueDate(LocalDate dueDate) {
                this.dueDate = dueDate;
        }

        public boolean isPaid() {
                return paid;
        }

        public void setPaid(boolean paid) {
                this.paid = paid;
        }

        public LocalDate getPaidOn() {
                return paidOn;
        }

        public void setPaidOn(LocalDate paidOn) {
                this.paidOn = paidOn;
        }

        private LocalDate dueDate;

        private  double amount;

        public double getAmount() {
                return amount;
        }

        public void setAmount(double amount) {
                this.amount = amount;
        }

        private boolean paid;
        private LocalDate paidOn;


}
