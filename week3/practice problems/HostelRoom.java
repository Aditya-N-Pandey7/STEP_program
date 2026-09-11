public class HostelRoom {
    private String roomNo;
    private int beds;
    private int occupied;

    public HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    public void allot(String name) {
        if (occupied < beds) {
            occupied++;
            System.out.println(name + " allotted to room " + roomNo);
        }
    }

    public static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        if (rooms == null) return null;

        for (HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }
        return null;
    }

    public static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom room = findAvailableRoom(rooms);

        if (room != null) {
            room.allot(studentName);
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    public static void main(String[] args) {
        HostelRoom[] available = {
            new HostelRoom("C-214", 3, 2),
            new HostelRoom("C-507", 2, 2)
        };

        safeAllot(available, "Divya");

        HostelRoom[] full = {
            new HostelRoom("C-214", 3, 3),
            new HostelRoom("C-507", 2, 2)
        };

        safeAllot(full, "Divya");
    }

    // The array contains references to HostelRoom objects. Passing the array passes its reference,
    // so the methods can mutate the same HostelRoom objects rather than copying each room.
}
