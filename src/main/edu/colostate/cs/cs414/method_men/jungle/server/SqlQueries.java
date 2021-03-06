package edu.colostate.cs.cs414.method_men.jungle.server;



import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;


public interface SqlQueries {

    /**
     * Adds Username and Password to user table.
     * @param Username: not null String.
     * @param Password: not null String.
     * @return True if added. False otherwise.
     */
    @SqlUpdate("INSERT INTO user(Username,Password) values(:Username, :Password)")
    boolean addUser(@Bind("Username") String Username, @Bind("Password")String Password);

    /**
     * Adds User1, User2, State, and Start_Date to match_state table field ID is auto generated
     * @param User1: not null String and must be in user table
     * @param User2: not null String and must be in user table
     * @param State: String
     * @param Start_Date String in format DDMMYYYYTTTT
     * @return ID: Long unique to match_state row
     */
    @SqlUpdate("INSERT INTO match_state(User1,User2,State,Start_Date) values(:User1,:User2,:State,:Start_Date)")
    @GetGeneratedKeys("ID")
    Long addMatchState(@Bind("User1") String User1, @Bind("User2")String User2, @Bind("State") String State, @Bind("Start_Date")String Start_Date);

    /**
     * Adds Winner, Loser, Game_Start, and Game_End to match_record table
     * @param Winner: not null String and must be in user table
     * @param Loser: not null String and must be in user table
     * @param Game_Start String in format DDMMYYYYTTTT
     * @param Game_End String in format DDMMYYYYTTTT
     * @return ID: Long unique to match_record row
     */
    @SqlUpdate("INSERT INTO match_record(Winner,Loser,Game_Start,Game_End) values(:Winner,:Loser,:Game_Start,:Game_End)")
    @GetGeneratedKeys("ID")
    Long addMatchRecord(@Bind("Winner") String Winner, @Bind("Loser")String Loser, @Bind("Game_Start") String Game_Start, @Bind("Game_End")String Game_End);

    /**
     * Adds Inviter and Invitee to match_invite table
     * @param Inviter: not null String and must be in user table
     * @param Invitee: not null String and must be in user table
     * @return True if added. False otherwise.
     */
    @SqlUpdate("INSERT INTO match_invite(Inviter,Invitee) values(:Inviter,:Invitee)")
    boolean addMatchInvite(@Bind("Inviter") String Inviter, @Bind("Invitee")String Invitee);

    /**
     * Searches for Username in user table
     * @param Username String
     * @return String with Username if in table or null if not in user table
     */
    @SqlQuery("SELECT Username FROM user WHERE Username=:Username")
    String searchUser(@Bind("Username") String Username);

    /**
     * Searches for exact Username and Password in user table
     * @param Username: String
     * @param Password: String
     * @return String Username if in table or null if not in user table
     */
    @SqlQuery("SELECT Username FROM user WHERE Username=:Username AND Password=:Password")
    String searchUserPassword(@Bind("Username") String Username, @Bind("Password")String Password);

    /**
     * Searches for match_state by matching ID
     * @param ID: Long unique in match_state
     * @return String State: String representation of the state of the match in match_state table
     * which can be null
     */
    @SqlQuery("SELECT State FROM match_state WHERE ID=:ID")
    String searchStateMatchState(@Bind("ID") Long ID);

    @SqlQuery("SELECT User1 FROM match_state WHERE ID=:ID;")
    String searchMatchUser1FromID(@Bind("ID") Long ID);

    @SqlQuery("SELECT User2 FROM match_state WHERE ID=:ID;")
    String searchMatchUser2FromID(@Bind("ID") Long ID);

    @SqlQuery("SELECT *  FROM match_state WHERE User1=:Username OR User2=:Username;")
    List< List<String> > searchUserMatchState(@Bind("Username") String Username);


    /**
     * Searches for Start_Date in match_state table by matching ID
     * @param ID: Long unique
     * @return String Start_Date: String in format DDMMYYYYTTTT in match_state or null if not in table
     */
    @SqlQuery("SELECT Start_Date FROM match_state WHERE ID=:ID")
    String searchStartDateMatchState(@Bind("ID") Long ID);

    /**
     * Searches for row in match_record table matching Winner
     * @param Winner: not null String and must be in user table
     * @return List<Long>: IDs from rows from match_record table with matching Winners or empty list if no matches
     */
    @SqlQuery("SELECT ID from match_record WHERE Winner=:Winner")
    List<Long> searchWinnerMatchRecord(@Bind("Winner") String Winner);

    /**
     * Searches for row in match_record table matching Winner
     * @param Loser: not null String and must be in user table
     * @return List<Long>: IDs from rows from match_record table with matching Winners or empty list if no matches
     */
    @SqlQuery("SELECT ID from match_record WHERE Loser=:Loser")
    List<Long> searchLoserMatchRecord(@Bind("Loser") String Loser);

    /**
     * Searches for Inviter and Invitee in match_invite table by matching Inviter
     * @param Inviter: not null String and must be in user table
     * @return List<String>: Invitees from match_invite table with matching Inviter or empty list if no matches
     */
    @SqlQuery("SELECT Invitee from match_invite WHERE Inviter=:Inviter")
    List<String> searchMatchInviter(@Bind("Inviter") String Inviter);

    /**
     * Searches for Inviter and Invitee in match_invite table by matching Invitee
     * @param Invitee: not null String and must be in user table
     * @return List<String>: Inviters from match_invite table with matching Invitee or empty list if no matches
     */
    @SqlQuery("SELECT Inviter from match_invite WHERE Invitee=:Invitee")
    List<String> searchMatchInvitee(@Bind("Invitee") String Invitee);

    /**
     * Searches for Inviter and Invitee pair in match_invite table by matching Inviter and Invitee
     * @param Inviter: not null String and must be in user table
     * @param Invitee: not null String and must be in user table
     * @return String: Inviters from match_invite table with matching Inviter and or empty list if no matches
     */
    @SqlQuery("SELECT Inviter from match_invite WHERE Inviter=:Inviter AND Invitee=:Invitee OR Inviter=:Invitee AND Invitee=:Inviter;")
    List<String> searchPairMatchInvite(@Bind("Inviter") String Inviter, @Bind("Invitee") String Invitee);

    /**
     * Gets row of match_state and maps result to DBRecord class matching ID
     * @param ID Long unique
     * @return DBRecord where ID,string1,string2,string3,string4 are set from match_state row if ID in DB or null if not
     */
    @SqlQuery("SELECT * FROM match_state WHERE ID=:ID")
    @RegisterRowMapper(DBRecordMapper.class)
    DBRecord searchRowIDMatchState(@Bind("ID") Long ID);

    /**
     * Gets row of match_record and maps result to DBRecord class matching ID
     * @param ID Long unique
     * @return DBRecord where ID,string1,string2,string3,string4 are set from match_record row if ID in DB or null if not
     */
    @SqlQuery("SELECT * FROM match_record WHERE ID=:ID")
    @RegisterRowMapper(DBRecordMapper.class)
    DBRecord searchRowIDMatchRecord(@Bind("ID") Long ID);

    /**
     * Gets rows of match_record and maps result to MatchRecordRow class matching where user is in a match_record row
     * @param user String from user table
     * @return DBRecord where ID,string1,string2,string3,string4 are set from match_record row or empty list if no matches
     */
    @SqlQuery("SELECT * FROM match_record WHERE Winner=:user OR Loser=:user")
    @RegisterRowMapper(DBRecordMapper.class)
    List<DBRecord> searchRowUserMatchRecord(@Bind("user") String user);

    /**
     * Gets rows of match_state and maps result to MatchRowState class matching where User1 is in a match_state row
     * @param User1
     * @return List<DBRecord> where ID,string1,string2,string3,string4 are set from match_state row or empty list if no matches
     */
    @SqlQuery("SELECT * FROM match_state WHERE User1=:User1 OR User2=:User1")
    @RegisterRowMapper(DBRecordMapper.class)
    List<DBRecord> searchRowUserMatchState(@Bind("User1") String User1);

    /**
     * Gets list of all users in user table
     * @return List<String> list of users in user table
     */
    @SqlQuery("SELECT Username FROM user")
    List<String> searchAllUsers();

    /**
     * Updates State in match_state table matching ID
     * @param State: String representation of the state of the match in match_state table
     * @param ID: Long unique
     * @return True if updated. False otherwise.
     */
    @SqlUpdate("UPDATE match_state SET State=:State WHERE ID=:ID")
    boolean updateMatchState(@Bind("State") String State, @Bind("ID")Long ID);

    /**
     * Deletes Username from user table matching Username
     * @param Username: String
     * @return True if deleted. False otherwise.
     */
    @SqlUpdate("DELETE FROM user WHERE Username=:Username")
    boolean deleteUser(@Bind("Username") String Username);

    /**
     * Deletes match_state row from match_state table matching ID
     * @param ID: Long unique
     * @return True if deleted. False otherwise.
     */
    @SqlUpdate("DELETE FROM match_state WHERE ID=:ID")
    boolean deleteMatchState(@Bind("ID") Long ID);

    /**
     * Deletes match_record row from match_record table matching ID
     * @param ID: Long unique
     * @return True if deleted. False otherwise.
     */
    @SqlUpdate("DELETE FROM match_record WHERE ID=:ID")
    boolean deleteMatchRecord(@Bind("ID") Long ID);

    /**
     * Deletes match_invite row from match_invite table matching Inviter
     * @param Inviter: not null String and must be in user table
     * @return True if deleted. False otherwise.
     */
    @SqlUpdate("DELETE FROM match_invite WHERE Inviter=:Inviter")
    boolean deleteMatchInvite(@Bind("Inviter") String Inviter);

    /**
     * Changes Username to deletedUser in match_record rows matching Winner
     * @param Winner: not null String and must be in user table
     * @return True if deleted. False otherwise.
     */
    @SqlUpdate("UPDATE match_record SET Winner='deletedUser' WHERE Winner=:Winner")
    boolean deleteWinnerMatchRecord(@Bind("Winner") String Winner);

    /**
     * Changes Username to deletedUser in match_record rows matching Loser
     * @param Loser: not null String and must be in user table
     * @return True if deleted. False otherwise.
     */
    @SqlUpdate("UPDATE match_record SET Loser='deletedUser' WHERE Loser=:Loser")
    boolean deleteLoserMatchRecord(@Bind("Loser") String Loser);

}
