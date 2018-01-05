package com.revature.projects.trms.dao;

import com.revature.projects.trms.beans.Event;
import com.revature.projects.trms.beans.InformationRequest;
import com.revature.projects.trms.beans.Reimbursement;
import com.revature.projects.trms.beans.ReimbursementResponse;
import com.revature.projects.trms.beans.ReimbursementStatus;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ReimbursementDataAccessObject extends GenericDataAccessObject<Reimbursement> {
	private HashMap<Integer, String> reimbursementStatuses;
	private HashMap<String, Integer> reimbursementStatusesReversed;
	private EventDataAccessObject eventDao;
	private InformationRequestDataAccessObject informationRequestDao;
	private ReimbursementResponseDataAccessObject reimbursementResponseDao;

  public ReimbursementDataAccessObject() {
		super();
		reimbursementStatuses = new HashMap<Integer, String>();
		reimbursementStatusesReversed = new HashMap<String, Integer>();
		eventDao = new EventDataAccessObject();
		informationRequestDao = new InformationRequestDataAccessObject();
		reimbursementResponseDao = new ReimbursementResponseDataAccessObject();
    init();
	}

	private void init() {
		ReimbursementStatusDataAccessObject rsDao = new  ReimbursementStatusDataAccessObject();
		List<ReimbursementStatus> reimbursementStatusList = rsDao.getAll();
		
		for(ReimbursementStatus rStatus : reimbursementStatusList) {
			reimbursementStatuses.put(rStatus.getReimbursementStatusId(), rStatus.getReimbursementStatus());
			reimbursementStatusesReversed.put(rStatus.getReimbursementStatus(), rStatus.getReimbursementStatusId());
		}
	}
	
	private void populateList(List<Reimbursement> reimbursements, ResultSet rs) throws SQLException {
		
		List<InformationRequest> infoRequests = null;
		Event event = null;
		ReimbursementResponse reimResponse = null;

		while(rs.next()) {
			int reimbursementId = rs.getInt("ReimbursementID");
			int requesterId = rs.getInt("RequesterID");
			String description = rs.getString("Description");
			String workRelatedJustification = rs.getString("WorkRelatedJustification");
			BigDecimal amountRequested = rs.getBigDecimal("ReimbursementAmountRequested");
			String additionalInformation = rs.getString("AdditionalInformation");
			int reimbursementStatusId = rs.getInt("ReimbursementStatusID");
			ZonedDateTime dateSubmitted = rs.getObject("DateSubmitted", ZonedDateTime.class);
			ZonedDateTime dateCompleted = rs.getObject("DateCompleted", ZonedDateTime.class);
			int eventId = rs.getInt("EventID");
			int reimbursementResponseId = rs.getInt("ReimbursementResponseID");
			
			event = eventDao.getById(eventId);
			infoRequests = informationRequestDao.getByAttribute("ReimbursementID", reimbursementId);
			reimResponse = reimbursementResponseDao.getById(reimbursementResponseId);
			if(infoRequests.size() == 0) infoRequests = null;

			reimbursements.add(new Reimbursement(
				reimbursementId,
				requesterId,
				description,
				workRelatedJustification,
				amountRequested,
				additionalInformation,
				reimbursementStatuses.get(reimbursementStatusId),
				dateSubmitted,
				dateCompleted,
				event,
				reimResponse,
				infoRequests
			));
		}
	}

	@Override
	public List<Reimbursement> getAll() {
    List<Reimbursement> reimbursements = new LinkedList<Reimbursement>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
			String sql = "select * from Reimbursement";
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			populateList(reimbursements, rs);
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

    return reimbursements;
	}

	@Override
	public Reimbursement getById(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Reimbursement reimbursement = null;

		try {
			String sql = "select * from Reimbursement where ReimbursementID=?";
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if(rs.next()) {
				Event event = null;
				List<InformationRequest> infoRequests = null;
				ReimbursementResponse reimResponse = null;
				int reimbursementId = rs.getInt("ReimbursementID");
				int requesterId = rs.getInt("RequesterID");
				String description = rs.getString("Description");
				String workRelatedJustification = rs.getString("WorkRelatedJustification");
				BigDecimal amountRequested = rs.getBigDecimal("ReimbursementAmountRequested");
				String additionalInformation = rs.getString("AdditionalInformation");
				int reimbursementStatusId = rs.getInt("ReimbursementStatusID");
				ZonedDateTime dateSubmitted = rs.getObject("DateSubmitted", ZonedDateTime.class);
				ZonedDateTime dateCompleted = rs.getObject("DateCompleted", ZonedDateTime.class);
				int eventId = rs.getInt("EventID");
				int reimbursementResponseId = rs.getInt("ReimbursementResponseID");
				
				event = eventDao.getById(eventId);
				infoRequests = informationRequestDao.getByAttribute("ReimbursementID", reimbursementId);
				reimResponse = reimbursementResponseDao.getById(reimbursementResponseId);
				if(infoRequests.size() == 0) infoRequests = null;

				reimbursement = new Reimbursement(
					reimbursementId,
					requesterId,
					description,
					workRelatedJustification,
					amountRequested,
					additionalInformation,
					reimbursementStatuses.get(reimbursementStatusId),
					dateSubmitted,
					dateCompleted,
					event,
					reimResponse,
					infoRequests
				);
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
		
		return reimbursement;
	}

	@Override
	public List<Reimbursement> getByAttribute(String attributeName, Object attributeValue) {
		List<Reimbursement> reimbursements = new LinkedList<Reimbursement>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "select * from Reimbursement where " + attributeName + "=?";

			ps = connection.prepareStatement(sql);
			
			if(attributeName.endsWith("ID"))
				ps.setInt(1, (Integer) attributeValue);
			else if(attributeName.endsWith("Date"))
				ps.setTimestamp(1, Timestamp.from(((ZonedDateTime) attributeValue).toInstant()));
			else if(attributeName.equalsIgnoreCase("ReimbursementAmountRequested"))
				ps.setBigDecimal(1, (BigDecimal) attributeValue);
			else
				ps.setString(1, (String) attributeValue);

				rs = ps.executeQuery();

				populateList(reimbursements, rs);
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

		return reimbursements;
	}

	@Override
	public void createNew(Reimbursement e) {
		PreparedStatement ps = null;

		try {
			String sql = "insert into Reimbursement (RequesterID, Description, WorkRelatedJustification, ReimbursementAmountRequested, AdditionalInformation, ReimbursementStatusID, DateSubmitted, EventID) values (?, ?, ?, ?, ?, ?, ?, ?)";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, e.getReimbursementId());
			ps.setString(2, e.getDescription());
			ps.setString(3, e.getWorkRelatedJustification());
			ps.setBigDecimal(4, e.getAmountRequested());
			ps.setString(5, e.getAdditionalInformation());
			ps.setInt(6, reimbursementStatusesReversed.get(e.getReimbursementStatus()));
			ps.setTimestamp(7, Timestamp.from(e.getDateSubmitted().toInstant()));
			ps.setInt(8, e.getEvent().getEventId());

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
		PreparedStatement ps = null;
		
		try {
			String sql = "delete from Reimbursement where ReimbursementID=?";
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !ps.isClosed()) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteByAttribute(String attributeName, Object attributeValue) {
		PreparedStatement ps = null;
		
		try {
			String sql = "delete from Reimbursement where " + attributeValue + "=?";
			ps = connection.prepareStatement(sql);
			
			if(attributeName.endsWith("ID"))
				ps.setInt(1, (Integer) attributeValue);
			else if(attributeName.endsWith("Date"))
				ps.setTimestamp(1, Timestamp.from(((ZonedDateTime) attributeValue).toInstant()));
			else if(attributeName.equalsIgnoreCase("ReimbursementAmountRequested"))
				ps.setBigDecimal(1, (BigDecimal) attributeValue);
			else
				ps.setString(1, (String) attributeValue);
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !ps.isClosed()) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateAttribute(int id, String attributeName, Object attributeValue) {
		PreparedStatement ps = null;
		
		try {
			String sql = "update Reimbursement set " + attributeValue + "=? where ReimbursementID=?";
			ps = connection.prepareStatement(sql);
			
			if(attributeName.endsWith("ID"))
				ps.setInt(1, (Integer) attributeValue);
			else if(attributeName.endsWith("Date"))
				ps.setTimestamp(1, Timestamp.from(((ZonedDateTime) attributeValue).toInstant()));
			else if(attributeName.equalsIgnoreCase("ReimbursementAmountRequested"))
				ps.setBigDecimal(1, (BigDecimal) attributeValue);
			else
				ps.setString(1, (String) attributeValue);
			
			ps.setInt(2, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !ps.isClosed()) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getCount() {
		int reimbursementCount = 0;
    CallableStatement cs = null;

    try {
      String sql = "{ CALL SP_Get_Reimbursement_Count(?) }";
      cs = connection.prepareCall(sql);
      cs.registerOutParameter(1, Types.INTEGER);
      cs.execute();
      reimbursementCount = cs.getInt(1);
    } catch (SQLException e) {
      System.err.println("Error calling stored procedure for getting Reimbursement count.");
    } finally {
			try {
				if(cs != null && !cs.isClosed()) cs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    return reimbursementCount;
	}

	@Override
  public int getCurrentID() {
    CallableStatement cs = null;
    int currentID = 0;

    try {
      String sql = "{ CALL SP_Get_Curr_ReimbursementID(?) }";
      cs = connection.prepareCall(sql);
      cs.registerOutParameter(1, Types.INTEGER);
      cs.execute();
      currentID = cs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(cs != null && !cs.isClosed()) cs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return currentID;
  }
}