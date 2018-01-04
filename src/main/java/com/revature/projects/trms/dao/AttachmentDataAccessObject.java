package com.revature.projects.trms.dao;

import com.revature.projects.trms.beans.Attachment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AttachmentDataAccessObject extends GenericDataAccessObject<Attachment> {

  public AttachmentDataAccessObject() {
    super();
  }

  private void populateList(List<Attachment> attachments, ResultSet rs) throws SQLException {
    while(rs.next()) {
      int attachmentId = rs.getInt("AttachmentID");
      int reimbursementId = rs.getInt("ReimbursementID");
      String attachmentLocation = rs.getString("AttachmentLocation");
      String attachmentDescription = rs.getString("AttachmentDescription");

      attachments.add(new Attachment(attachmentId, reimbursementId, attachmentLocation, attachmentDescription));
    }
  }

	@Override
	public List<Attachment> getAll() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Attachment> attachments = new LinkedList<Attachment>();

    try {
      String sql = "select * from Attachment";
      ps = connection.prepareStatement(sql);

      rs = ps.executeQuery();
      populateList(attachments, rs);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      try {
        if(rs != null && !rs.isClosed()) rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return attachments;
	}

	@Override
	public Attachment getById(int id) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Attachment a = null;

    try {
      String sql = "select * from  Attachment where AttachmentID=?";
      ps = connection.prepareStatement(sql);
      ps.setInt(1, id);
      rs = ps.executeQuery();

      if(rs.next()) {
        int attachmentId = rs.getInt("AttachmentID");
        int reimbursementId = rs.getInt("ReimbursementID");
        String attachmentLocation = rs.getString("AttachmentLocation");
        String attachmentDescription = rs.getString("AttachmentDescription");

        a = new Attachment(attachmentId, reimbursementId, attachmentLocation, attachmentDescription);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      try {
        if(rs != null && !rs.isClosed()) rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

		return a;
	}

	@Override
	public List<Attachment> getByAttribute(String attributeName, Object attributeValue) {
    List<Attachment> attachments = new LinkedList<Attachment>();
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
      String sql = "select * from Attachment where " + attributeValue + "=?";
      ps = connection.prepareStatement(sql);
      if(attributeName.endsWith("ID"))
        ps.setInt(1, (Integer)attributeValue);
      else
        ps.setString(1, (String) attributeValue);
      
      rs = ps.executeQuery();

      populateList(attachments, rs);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      try {
        if(rs != null && !rs.isClosed()) rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

		return attachments;
	}

	@Override
	public void createNew(Attachment e) {
    PreparedStatement ps = null;

    try {
      String sql = "insert into Attachment (AttachmentLocation, ReimbursementID, AttachmentDescription) values (?, ?, ?)";
      
      ps = connection.prepareStatement(sql);
      ps.setString(1, e.getAttachmentLocation());
      ps.setInt(2, e.getReimbursementId());
      ps.setString(3, e.getAttachmentDescription());
      ps.execute();
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
	}

	@Override
	public void deleteById(int id) {
		
	}

	@Override
	public void deleteByAttribute(String attributeName, Object attributeValue) {
		
	}

	@Override
	public void updateAttribute(int id, String attributeName, Object attributeValue) {
		
	}

	@Override
	public int getCount() {
		return 0;
	}

}