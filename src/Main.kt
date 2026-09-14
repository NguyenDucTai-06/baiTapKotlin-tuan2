data class Student(
    var id: String,
    var fullName: String,
    var age: Int,
    var major: String,
    var gpa: Double
) {
    override fun toString(): String {
        return "ID: $id | Name: $fullName | Age: $age | Major: $major | GPA: $gpa"
    }
}

class StudentManager {
    private val students = mutableListOf<Student>()

    init {
        addStudent(Student("SV001", "Nguyễn Đức Tài", 20, "Information Technology", 8.5))
        addStudent(Student("SV002", "Lê Anh Quốc", 21, "Computer Science", 9.2))
        addStudent(Student("SV003", "Nguyễn Võ Duy Sơn", 22, "Software Engineering", 7.5))
        addStudent(Student("SV004", "Đỗ Đình Sỹ", 19, "Information Technology", 4.5))
        addStudent(Student("SV005", "Võ Văn Phương", 20, "Data Science", 8.0))
    }

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun displayAll() {
        if (students.isEmpty()) println("=> Danh sách trống!")
        else students.forEach { println(it) }
    }

    fun searchById(id: String): Student? {
        return students.find { it.id.equals(id, ignoreCase = true) }
    }

    fun searchByName(namePart: String) {
        val result = students.filter { it.fullName.contains(namePart, ignoreCase = true) }
        if (result.isEmpty()) println("=> Không tìm thấy!") else result.forEach { println(it) }
    }

    fun filterByMajor(major: String) {
        val result = students.filter { it.major.equals(major, ignoreCase = true) }
        if (result.isEmpty()) println("=> Không tìm thấy!") else result.forEach { println(it) }
    }

    fun filterByGpaRange(min: Double, max: Double) {
        val result = students.filter { it.gpa in min..max }
        if (result.isEmpty()) println("=> Không tìm thấy!") else result.forEach { println(it) }
    }

    fun getAverageGpa(): Double {
        return if (students.isNotEmpty()) students.map { it.gpa }.average() else 0.0
    }

    fun calculateMajorAverage(major: String) {
        val majorStudents = students.filter { it.major.equals(major, ignoreCase = true) }
        val majorAvg = if (majorStudents.isNotEmpty()) majorStudents.map { it.gpa }.average() else 0.0
        println("=> Trung bình GPA ngành $major: $majorAvg")
    }

    fun countGpaGreaterThanEqual(value: Double): Int = students.count { it.gpa >= value }
    fun countGpaLessThan(value: Double): Int = students.count { it.gpa < value }

    fun getHighestGpaStudent() = students.maxByOrNull { it.gpa }
    fun getOldestStudent() = students.maxByOrNull { it.age }

    fun removeStudent(id: String): Boolean {
        return students.removeIf { it.id.equals(id, ignoreCase = true) }
    }

    fun getSortedByGpaDesc() = students.sortedByDescending { it.gpa }
    fun getTop3Gpa() = students.sortedByDescending { it.gpa }.take(3)
    fun getSortedByAge() = students.sortedBy { it.age }
    fun getSortedByName() = students.sortedBy { it.fullName }
}

fun main() {
    val manager = StudentManager()

    while (true) {
        // Menu chính xác theo yêu cầu đề bài
        println("\n========== STUDENT MANAGEMENT ==========")
        println("1. Add student")
        println("2. Display all students")
        println("3. Search student")
        println("4. Calculate average GPA")
        println("5. Find student with highest GPA")
        println("6. Remove student")
        println("0. Exit ========================================")
        print("Choose: ")

        when (readLine()?.trim()) {
            "1" -> {
                print("Enter ID: "); val id = readLine() ?: ""
                print("Enter Full Name: "); val name = readLine() ?: ""
                print("Enter Age: "); val age = readLine()?.toIntOrNull() ?: 0
                print("Enter Major: "); val major = readLine() ?: ""
                print("Enter GPA: "); val gpa = readLine()?.toDoubleOrNull() ?: 0.0

                manager.addStudent(Student(id, name, age, major, gpa))
                println("=> Thêm sinh viên thành công!")
            }
            "2" -> {
                println("\n--- TÙY CHỌN HIỂN THỊ ---")
                println("1. Hiển thị bình thường")
                println("2. Sắp xếp sinh viên theo GPA giảm dần")
                println("3. Hiển thị 3 sinh viên có GPA cao nhất")
                println("4. Sắp xếp sinh viên theo tuổi")
                println("5. Sắp xếp sinh viên theo tên")
                print("Chọn (1-5): ")
                when (readLine()?.trim()) {
                    "1" -> manager.displayAll()
                    "2" -> manager.getSortedByGpaDesc().forEach { println(it) }
                    "3" -> manager.getTop3Gpa().forEach { println(it) }
                    "4" -> manager.getSortedByAge().forEach { println(it) }
                    "5" -> manager.getSortedByName().forEach { println(it) }
                    else -> println("=> Lựa chọn không hợp lệ!")
                }
            }
            "3" -> {
                println("\n--- TÙY CHỌN TÌM KIẾM ---")
                println("1. Tìm theo ID")
                println("2. Tìm theo một phần tên")
                println("3. Tìm tất cả sinh viên thuộc một ngành")
                println("4. Tìm sinh viên có GPA trong khoảng 7.0 -> 8.5")
                print("Chọn (1-4): ")
                when (readLine()?.trim()) {
                    "1" -> {
                        print("Nhập ID: ")
                        val st = manager.searchById(readLine() ?: "")
                        if (st != null) println(st) else println("=> Không tìm thấy!")
                    }
                    "2" -> {
                        print("Nhập từ khóa tên: ")
                        manager.searchByName(readLine() ?: "")
                    }
                    "3" -> {
                        print("Nhập ngành: ")
                        manager.filterByMajor(readLine() ?: "")
                    }
                    "4" -> manager.filterByGpaRange(7.0, 8.5)
                    else -> println("=> Lựa chọn không hợp lệ!")
                }
            }
            "4" -> {
                println("\n--- TÙY CHỌN TÍNH TOÁN & THỐNG KÊ ---")
                println("1. Tính GPA trung bình toàn trường")
                println("2. Tính GPA trung bình của sinh viên ngành được giao")
                println("3. Đếm số sinh viên có GPA >= 8.0")
                println("4. Đếm số sinh viên có GPA < 5.0")
                print("Chọn (1-4): ")
                when (readLine()?.trim()) {
                    "1" -> println("=> GPA trung bình toàn trường: ${manager.getAverageGpa()}")
                    "2" -> {
                        print("Nhập ngành cần tính: ")
                        manager.calculateMajorAverage(readLine() ?: "")
                    }
                    "3" -> println("=> Số lượng sinh viên: ${manager.countGpaGreaterThanEqual(8.0)}")
                    "4" -> println("=> Số lượng sinh viên: ${manager.countGpaLessThan(5.0)}")
                    else -> println("=> Lựa chọn không hợp lệ!")
                }
            }
            "5" -> {
                println("\n--- TÌM SINH VIÊN ĐẶC BIỆT ---")
                println("1. Tìm sinh viên có GPA cao nhất")
                println("2. Tìm sinh viên lớn tuổi nhất")
                print("Chọn (1-2): ")
                when (readLine()?.trim()) {
                    "1" -> {
                        println("=> Sinh viên có GPA cao nhất:")
                        manager.getHighestGpaStudent()?.let { println(it) }
                    }
                    "2" -> {
                        println("=> Sinh viên lớn tuổi nhất:")
                        manager.getOldestStudent()?.let { println(it) }
                    }
                    else -> println("=> Lựa chọn không hợp lệ!")
                }
            }
            "6" -> {
                print("Nhập ID sinh viên cần xóa: ")
                if (manager.removeStudent(readLine() ?: "")) {
                    println("=> Xóa thành công!")
                } else {
                    println("=> Không tìm thấy sinh viên.")
                }
            }
            "0" -> {
                println("Exiting Program...")
                return
            }
            else -> println("Lựa chọn không hợp lệ. Vui lòng thử lại.")
        }
    }
}