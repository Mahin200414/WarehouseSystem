package wwa;

public class SharedWarehouseState {

    private int apples;
    private int oranges;

    private boolean accessing = false;         
    private int threadsWaiting = 0;            
    public SharedWarehouseState(int initialApples, int initialOranges) {
        this.apples = initialApples;
        this.oranges = initialOranges;
    }


    private synchronized void acquireLock() {
        Thread thread = Thread.currentThread();
        threadsWaiting++;
        System.out.println("\n" + thread.getName() + " attempting to acquire lock...");

        while (accessing) {
            try {
                System.out.println(thread.getName() + " waiting...");
                wait();
            } catch (InterruptedException e) {}
        }

        threadsWaiting--;
        accessing = true;
        System.out.println(thread.getName() + " acquired lock.");
    }

    
    private synchronized void releaseLock() {
        accessing = false;
        notifyAll();
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " released lock.");
    }

   

    public String checkStock() {
        acquireLock();
        try {
            return "STOCK,apples=" + apples + ",oranges=" + oranges;
        } finally {
            releaseLock();
        }
    }

    public String buyApples(int number) {
        acquireLock();
        try {
            if (number <= 0) return "ERROR,Buy_apples must be positive";
            if (number > apples) return "ERROR,Only " + apples + " apples available";
            apples -= number;
            return "OK,Bought " + number + " apples; apples now=" + apples + ",oranges=" + oranges;
        } finally {
            releaseLock();
        }
    }

    public String buyOranges(int number) {
        acquireLock();
        try {
            if (number <= 0) return "ERROR,Buy_oranges must be positive";
            if (number > oranges) return "ERROR,Only " + oranges + " oranges available";
            oranges -= number;
            return "OK,Bought " + number + " oranges; apples=" + apples + ",oranges=" + oranges;
        } finally {
            releaseLock();
        }
    }

    public String addApples(int number) {
        acquireLock();
        try {
            if (number <= 0) return "ERROR,Add_apples must be positive";
            apples += number;
            return "OK,Added " + number + " apples; apples now=" + apples + ",oranges=" + oranges;
        } finally {
            releaseLock();
        }
    }

    public String addOranges(int number) {
        acquireLock();
        try {
            if (number <= 0) return "ERROR,Add_oranges must be positive";
            oranges += number;
            return "OK,Added " + number + " oranges; apples now=" + apples + ",oranges=" + oranges;
        } finally {
            releaseLock();
        }
    }
}

