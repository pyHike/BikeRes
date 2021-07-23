public class Booking {     
    
    private String userName;
    private String routeCode;
    private double price;
    public String bookingNum;               // key
    private int seats;
    private boolean paidFor;
    
    public Booking(String userName, String routeCode, 
            double price, int seats, boolean paidFor) {
//        ShuttleRun.hasCapacity(routeCode, seats);
        if ( User.userDirectory.containsKey(userName) ) {
            this.userName = userName;
        }
        else {
            System.out.println("The user " + userName + " does not yet exist. Please create a new user or try again.");
            return;
        }
        if ( ShuttleRun.hasCapacity(routeCode, seats) ) {
            this.routeCode = routeCode;
            this.price = price;        
            this.seats = seats;
            this.paidFor = paidFor;
            this.bookingNum = String.format("%S-%7S-%02d", userName, routeCode, seats);
            this.newBooking(this.routeCode, this.userName, this.bookingNum, this.seats);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder z = new StringBuilder();
        String output = z.append("BookingNum: ").append(bookingNum)
                .append("\tuserName: ").append(userName).append("\trouteCode: ")
                .append(routeCode).append("\tprice: ").append(price)
                .append("\tseats: ").append(seats).toString();
        return output;
    }

    public boolean hasCapacity(String routeCode, int seats) {
        return ShuttleRun.hasCapacity(routeCode, seats);
    }
    
    // add booking to ShuttleRun Bookings HashMap & user's 
    public void newBooking(String routeCode, String userName, String bookingNum, int seats) {        
        ShuttleRun.updateInventory(routeCode, seats);
        Customer.addBooking(userName, bookingNum);
    }
    
    private void delBooking() {
        this.userName = null;
        this.routeCode = null;
        this.price = 0;
        this.bookingNum = null;
        this.paidFor = false;
        int z = ShuttleRun.shuttleBookings.get(routeCode);
        ShuttleRun.shuttleBookings.replace(userName, z, z-seats);
    }

    public double getPrice() {
        return price;
    }

    private void setPrice(double price) {
        this.price = price;
    }

    public int getSeats() {
        return seats;
    }

    private void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean getPaidFor() {
        return paidFor;
    }    
    
    private void setPaidFor(boolean paidFor) {
        this.paidFor = paidFor;
    }        
}
