package com.library.dao.impl;

import com.library.dao.MemberDao;
import com.library.model.Member;
import com.library.util.DbUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl implements MemberDao {

    @Override
    public boolean addMember(Member member) {
        String sql = "INSERT INTO members (name, email, phone, membership_date, max_books_allowed) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPhone());
            ps.setDate(4, Date.valueOf(member.getMembershipDate())); // LocalDate → SQL Date
            ps.setInt(5, member.getMaxBooksAllowed());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error adding member", e);
        }
    }

    @Override
    public Member getMemberById(int memberId) {
        String sql = "SELECT * FROM members WHERE member_id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToMember(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching member by ID", e);
        }

        return null;
    }

    @Override
    public List<Member> getAllMembers() {
        String sql = "SELECT * FROM members";
        List<Member> members = new ArrayList<>();

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                members.add(mapRowToMember(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching members", e);
        }

        return members;
    }

    @Override
    public boolean updateMember(Member member) {
        String sql = "UPDATE members SET name=?, email=?, phone=?, membership_date=?, max_books_allowed=? " +
                     "WHERE member_id=?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPhone());
            ps.setDate(4, Date.valueOf(member.getMembershipDate()));
            ps.setInt(5, member.getMaxBooksAllowed());
            ps.setInt(6, member.getMemberId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating member", e);
        }
    }

    @Override
    public boolean deleteMember(int memberId) {
        String sql = "DELETE FROM members WHERE member_id=?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting member", e);
        }
    }

    // 🔹 Helper method to map ResultSet → Member
    private Member mapRowToMember(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setMemberId(rs.getInt("member_id"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        member.setPhone(rs.getString("phone"));
        member.setMembershipDate(rs.getDate("membership_date").toLocalDate());
        member.setMaxBooksAllowed(rs.getInt("max_books_allowed"));
        return member;
    }
}
