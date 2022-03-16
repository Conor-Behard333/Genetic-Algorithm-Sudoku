public class Database {
    private Data[] data = new Data[5];

    protected void addData(Data data) {
        this.data[data.getRun() - 1] = data;
    }

    protected void printData() {
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
        }
    }
    
}
