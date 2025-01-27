package carRentingSystemLLD;

import java.util.*;
import java.time.*;
import java.time.temporal.*;
/*
 * 1. The system will support the renting of different automobiles like car, truck, SUV, van,
and motorbike. done
2. Each vehicle should be added with a unique barcode and other details including a
parking stall number which helps to locate the vehicle.
3. The system should be able to retrieve information like who took a particular vehicle
or what are the vehicles rented-out by a specific member.
4. The system should collect a late fee for vehicles returned after the due date.
5. Members should be able to search vehicle inventory and reserve any available
vehicle.
 */

public class CarRentSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class Barcode {
	private String identification;
	
	public Barcode(String identification) {
		super();
		this.identification = UUID.randomUUID().toString();
	}

	public String scanBarcode() {
		return identification;
	}
}

class NumberPlate {
	private String stateID;
	private String districtID;
	private String rtoNumber;
	public NumberPlate(String stateID, String districtID, String rtoNumber) {
		this.stateID = stateID;
		this.districtID = districtID;
		this.rtoNumber = rtoNumber;
	}
	@Override
	public String toString() {
		return "NumberPlate [" + stateID + " " + districtID + " " + rtoNumber + "]";
	}
	
}

enum PaymentStatus {
	PAID,
	PENDING,
	PARTIALLY_PAID,
}
class Member {
    private String memberId;
    private String name;
    private String email;
    private List<Reservation> reservations;

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}

enum AdditionalServiceType {
	CHILD_SEAT,
	SKI_RACK
}

class AdditionalService {
    private String serviceName;
    private double cost;
    private AdditionalServiceType serviceType;

    public AdditionalService(String serviceName, double cost, AdditionalServiceType serviceType) {
        this.serviceName = serviceName;
        this.cost = cost;
        this.serviceType = serviceType;
    }

    public double getCost() {
        return cost;
    }

    // Getters and Setters
}

class Reservation {
    private String reservationId;
    private Member member;
    private Automobile vehicle;
    private LocalDateTime reservationDate;
    private LocalDateTime pickupDate;
    private LocalDateTime dueDate;
    private PaymentStatus status; // PENDING, CONFIRMED, CANCELLED
    private List<AdditionalService> additionalServices;

    public Reservation(String reservationId, Member member, Automobile vehicle, LocalDateTime pickupDate, LocalDateTime dueDate) {
        this.reservationId = reservationId;
        this.member = member;
        this.vehicle = vehicle;
        this.pickupDate = pickupDate;
        this.dueDate = dueDate;
        this.status = PaymentStatus.PENDING;
        this.additionalServices = new ArrayList<>();
    }

    public void addService(AdditionalService service) {
        additionalServices.add(service);
    }

    public double calculateTotalCost() {
        double cost = vehicle.getRentalPrice();
        for (AdditionalService service : additionalServices) {
            cost += service.getCost();
        }
        return cost;
    }

    // Getters and Setters
}


class Calender {
	private String[] bookingForMonth;
	public Calender() {
		bookingForMonth = new String[30];
		for (int i = 0; i <= 30; i++) {
			bookingForMonth[i] = null;
		}
	}
	public Boolean isAvailable(int from, int to) {
		for (int i = from; i <= to; i++) {
			if (bookingForMonth[i] == null) return false;
		}
		return true;
	}
	public Boolean addBookingInInterval(int from, int to, String bookingID) {
		if (isAvailable(from, to)) {
			for (int i = from; i <= to; i++) {
				bookingForMonth[i] = bookingID;
			}
			System.out.println("Booking is added to calender");
		}
		System.out.println("Booking is not avaliable");
		return false;
	}
}

enum SearchType {
	NAME,
	CAR_TYPE,
}

class VehicleManager {
	private Map<String, Automobile> vehicleInventory;

	public VehicleManager() {
		super();
		this.vehicleInventory = new HashMap<String, Automobile>();
	}
	
	public List<Automobile> searchCars(SearchType type) {
		List<Automobile> result = new ArrayList<Automobile>();
		return result;
	}
}

abstract class Automobile {
	private String autoID;
	private VehicleType vehicleType;
	private Barcode barcode;
	private NumberPlate numberPlate;
	private String parkingID;
	private Calender calenderForStatus;
	private List<Reservation> bookingHistory;
    public abstract double getRentalPrice();
}

enum VehicleType {
	CAR,
	TRUCK,
	SUV
}

class VehicleLog {
    private String vehicleBarcode;
    private List<String> logEntries;

    public VehicleLog(String vehicleBarcode) {
        this.vehicleBarcode = vehicleBarcode;
        this.logEntries = new ArrayList<>();
    }

    public void addLogEntry(String entry) {
        logEntries.add(entry);
    }

    public List<String> getLogEntries() {
        return logEntries;
    }
}

class Notification {
    public static void sendReminder(Member member, String message) {
        System.out.println("Notification sent to " + member.getEmail() + ": " + message);
    }
}

class Payment {
    public static double calculateLateFee(LocalDateTime dueDate, LocalDateTime returnDate) {
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        return daysLate > 0 ? daysLate * 20.0 : 0.0; // $20 per late day
    }
}


class Car extends Automobile {
    @Override
    public double getRentalPrice() {
        System.out.println("Car has been rented.");
        return 10000.0;
    }
}

class Truck extends Automobile {
    @Override
    public double getRentalPrice() {
        System.out.println("Truck has been rented.");
        return 15000.0;
    }
}

class SUV extends Automobile {
    @Override
    public double getRentalPrice() {
        System.out.println("SUV has been rented.");
        return 20000.0;
    }
}

class AutomobileFactory {
    public static Automobile getAutomobile(VehicleType type) {
        switch (type) {
            case CAR:
                return new Car();
            case TRUCK:
                return new Truck();
            case SUV:
                return new SUV();
            default:
                throw new IllegalArgumentException("Unknown automobile type: " + type);
        }
    }
}