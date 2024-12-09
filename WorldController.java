package sim;

import java.util.Timer;
import java.util.TimerTask;

public class WorldController {
    private World world;         // Đối tượng quản lý hệ sinh thái
    private WorldView view;      // Giao diện đồ họa
    private Timer timer;         // Timer để quản lý tick

    private static final int DEFAULT_TICK_DURATION = 500; // 500 milliseconds (0.5 giây)
    private int tickDuration = DEFAULT_TICK_DURATION;     // Có thể thay đổi tick duration

    // Constructor
    public WorldController(World world, WorldView view) {
        this.world = world;
        this.view = view;
        this.timer = new Timer(); // Khởi tạo bộ đếm thời gian
    }

    // Bắt đầu mô phỏng
    public void startSimulation() {
        stopSimulation(); // Dừng bất kỳ timer nào đang chạy trước đó (nếu có)
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                world.update();        // Cập nhật trạng thái thế giới
                view.renderWorld(world); // Cập nhật giao diện người dùng
            }
        }, 0, tickDuration); // Tick đầu tiên chạy ngay lập tức, sau đó lặp lại mỗi tickDuration
        System.out.println("Simulation started with tick duration: " + tickDuration + " ms");
    }

    // Dừng mô phỏng
    public void stopSimulation() {
        if (timer != null) {
            timer.cancel(); // Hủy bỏ timer hiện tại
            System.out.println("Simulation stopped.");
        }
    }

    // Tính toán thống kê quần thể và năng lượng
    public void calculateStats() {
        int numOrganisms = world.getOrganisms().size(); // Lấy số lượng sinh vật
        System.out.println("Number of organisms: " + numOrganisms);
        // Có thể thêm các thống kê khác như tổng năng lượng, quần thể theo loại, v.v.
    }

    // Cấu hình lại sinh vật trong hệ sinh thái
    public void configOrganisms() {
        world.initializeOrganisms(WorldGenerator.generateDefaultOrganisms()); // Ví dụ: tạo sinh vật mặc định
        System.out.println("Organisms reconfigured.");
    }

    // Điều chỉnh thời gian tick
    public void setTickDuration(int newTickDuration) {
        if (newTickDuration <= 0) {
            throw new IllegalArgumentException("Tick duration must be greater than 0.");
        }
        this.tickDuration = newTickDuration;
        System.out.println("Tick duration set to: " + newTickDuration + " ms");
        startSimulation(); // Restart mô phỏng với tick mới
    }
}
