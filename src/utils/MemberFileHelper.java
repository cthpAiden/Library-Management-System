package utils;

import java.util.ArrayList;
import schema.Member;

public class MemberFileHelper extends FileHelper<Member> {

    public MemberFileHelper(ArrayList<Member> dataList) {
        super(dataList);
    }

    // line format: memberID|nameMember|email|phoneNumber
    @Override
    public Member handleLine(String line) {
        String[] p = line.split("\\|");
        return new Member(p[0], p[1], p[2], p[3]);
    }
}
