package lab.pkg01;

import java.util.ArrayList;
import java.util.Scanner;
import schema.*;

public class MemberManager {
    private final ArrayList<Member> memberList;
    private final ArrayList<Borrowing> borrowList;
    private final Scanner sc = book.sc;

    public MemberManager(ArrayList<Member> memberList, ArrayList<Borrowing> borrowList) {
        this.memberList = memberList;
        this.borrowList = borrowList;
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("================================");
            System.out.println("        MANAGE MEMBERS          ");
            System.out.println("================================");
            System.out.println("1. Add member");
            System.out.println("2. Display all members");
            System.out.println("3. Find member by ID");
            System.out.println("4. Remove member");
            System.out.println("5. Return");
            System.out.print("Select your choice: ");
            choice = InputHelper.readInt(sc);
            switch (choice) {
                case 1: addMember(); break;
                case 2: showAllMembers(); break;
                case 3:
                    System.out.print("Input Member ID: ");
                    Member mb = findMemberByID(sc.nextLine());
                    if (mb != null) { Member.showHeader(); mb.showInfor(); }
                    else System.out.println("Member not found!");
                    break;
                case 4:
                    System.out.print("Input Member ID: ");
                    deleteMember(sc.nextLine());
                    break;
                case 5: System.out.println("Returning to main menu..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    public void addMember() {
        Member mb = new Member();
        mb.inputInfor();
        if (findMemberByID(mb.getMemberID()) != null) {
            System.out.println("Member ID already existed!");
            return;
        }
        memberList.add(mb);
        System.out.println("Successfully added!");
    }

    public void showAllMembers() {
        if (memberList.isEmpty()) { System.out.println("Empty member list!"); return; }
        Member.showHeader();
        for (Member mb : memberList) mb.showInfor();
    }

    public Member findMemberByID(String memberID) {
        for (Member mb : memberList) {
            if (mb.getMemberID().equalsIgnoreCase(memberID)) return mb;
        }
        return null;
    }

    public void deleteMember(String memberID) {
        for (Borrowing br : borrowList) {
            if (br.getMemberID().equalsIgnoreCase(memberID) && !br.isReturned()) {
                System.out.println("Member is borrowing books, unable to remove!");
                return;
            }
        }
        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getMemberID().equalsIgnoreCase(memberID)) {
                memberList.remove(i);
                System.out.println("Successfully removed!");
                return;
            }
        }
        System.out.println("Member not found!");
    }
}
