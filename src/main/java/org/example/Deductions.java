package org.example;

public class Deductions {
    public static double computePagIbig() {
        return 200;
    }

    public static double computePhilHealth(double salary) {
        double contribution = salary * 0.05;
        if (contribution < 500) return 500 / 2;
        if (contribution > 4500) return 4500 / 2;
        return contribution / 2;
    }

    public static double computeSSS(double salary) {
        int MPFCounter = 20250;
        int counter2 = 1;
        int MPF = 0;

        if (salary >= 20250) {
            while (MPFCounter <= salary) {
                if (counter2 == 1) {
                    MPF += 25;
                    counter2 = 0;
                }
                MPFCounter += 500;
                counter2++;
            }
        }

        int PaymentCounter = 5250;
        int counter3 = 1;
        int payment = 250;

        if (salary < 20250 && salary >= 5250) {
            while (PaymentCounter <= salary) {
                if (counter3 == 1) {
                    payment += 25;
                    counter3 = 0;
                }
                PaymentCounter += 500;
                counter3++;
            }
        } else {
            payment = 1000;
        }

        if (salary >= 34750) {
            return 1750;
        } else if (salary >= 20250) {
            return payment + MPF;
        } else if (salary >= 5250) {
            return payment;
        } else {
            return 250;
        }
    }

    public static double computeIncomeTax(double gross, double pagibig, double philhealth, double sss) {
        double taxableIncome = gross - (pagibig + philhealth + sss);
        double incomeTax = 0;

        if (taxableIncome >= 20833 && taxableIncome <= 33332) {
            incomeTax = (taxableIncome - 20833) * 0.15;
        } else if (taxableIncome <= 66666) {
            incomeTax = ((taxableIncome - 33333) * 0.20) + 1875;
        } else if (taxableIncome <= 166666) {
            incomeTax = ((taxableIncome - 66667) * 0.25) + 8541.80;
        } else if (taxableIncome <= 666666) {
            incomeTax = ((taxableIncome - 166667) * 0.30) + 33541.80;
        } else if (taxableIncome > 666666) {
            incomeTax = ((taxableIncome - 666667) * 0.35) + 183541.80;
        }

        return Math.max(0, incomeTax);
    }
}
