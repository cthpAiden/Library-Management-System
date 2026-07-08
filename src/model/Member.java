package model;

public class Member {
    private String memberID;
    private String nameMember;
    private String email;
    private String phoneNumber;

    public Member(String memberID, String nameMember, String email, String phoneNumber) {
        this.memberID = memberID;
        this.nameMember = nameMember;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // getter
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

    // setter
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

    public static void showHeader() {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("|%-10s|%-25s|%-15s|%-25s|%n", "MemberID", "Name", "Phone", "Email");
    }

    public void showInfor() {
        System.out.printf("|%-10s|%-25s|%-15s|%-25s|%n",
                this.memberID, this.nameMember, this.phoneNumber, this.email);
    }

    // dòng ghi file: memberID|nameMember|email|phoneNumber
    @Override
    public String toString() {
        return memberID + "|" + nameMember + "|" + email + "|" + phoneNumber;
    }
}
