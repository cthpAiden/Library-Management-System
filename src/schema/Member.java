package schema;

public class Member {
    private String memberID;
    private String nameMember;
    private String email;
    private String phoneNumber;

    // constructor
    public Member() {
    }

    public Member(String memberID, String nameMember, String email, String phoneNumber) {
        this.memberID = memberID;
        this.nameMember = nameMember;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    //getter
    public String getMemberID() {
        return memberID;
    }

    public String getNameMember() {
        return nameMember;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    //setter
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public void setNameMember(String nameMember) {
        this.nameMember = nameMember;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //inputInfor
    public void inputInfor(){
        memberID = book.readRequiredText("ID Member: ");
        nameMember = book.readRequiredText("Member name: ");
        phoneNumber = book.readText("Phone number: ");
        email = book.readText("Email: ");
    }

    //showInfor
    public static void showHeader() {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("|%-10s|%-25s|%-15s|%-25s|%n", "MemberID", "Name", "Phone", "Email");
    }

    public void showInfor() {
        // ĐÃ SỬA: Bổ sung lệnh System.out.printf trực tiếp để hiển thị dữ liệu lên màn hình console
        System.out.printf("|%-10s|%-25s|%-15s|%-25s|%n",
                this.memberID, this.nameMember, this.phoneNumber, this.email);
    }

    // dòng ghi file: memberID|nameMember|email|phoneNumber
    @Override
    public String toString() {
        return memberID + "|" + nameMember + "|" + email + "|" + phoneNumber;
    }
}