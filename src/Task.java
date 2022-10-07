public class Task {
    String ID;
    String Name;
    String StartTime;
    String EndTime;

    int ReceiveNum;
    public Task(String ID, String name, String startTime, String endTime) {
        this.ID = ID;
        Name = name;
        StartTime = startTime;
        EndTime = endTime;
    }

    public String DescSelf() {
        return "[ID:" + this.ID +
                "] [Name:" + this.Name +
                "] [ReceiveNum:" + this.ReceiveNum+
                "] [StartTime:" + this.StartTime +
                "] [EndTime:" + this.EndTime +
                "]";
    }

}
