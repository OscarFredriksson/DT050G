package se.miun.distsys.listeners;

import se.miun.distsys.messages.ChatMessages.*;
import se.miun.distsys.messages.ConnectMessages.*;
import se.miun.distsys.messages.CoordinatorMessages.GenerateSequenceNumberMessage;
import se.miun.distsys.messages.CoordinatorMessages.GetUserIDMessage;
import se.miun.distsys.messages.CoordinatorMessages.SequenceNumberMessage;
import se.miun.distsys.messages.CoordinatorMessages.UserIDMessage;
import se.miun.distsys.messages.ElectionMessages.AliveMessage;
import se.miun.distsys.messages.ElectionMessages.ElectionMessage;
import se.miun.distsys.messages.ElectionMessages.VictoryMessage;

public interface ChatMessageListener {
    public void onIncomingChatMessage(ChatMessage chatMessage);

    public void onIncomingStatusMessage(StatusMessage statusMessage);

    public void onIncomingJoinMessage(JoinMessage joinMessage);

    public void onIncomingLeaveMessage(LeaveMessage leaveMessage);

    public void onIncomingAuthMessage(AuthMessage authMessage);

    public void onIncomingAssignMessage(AssignMessage assignMessage);

    public void onIncomingGenerateSequenceNumberMessage(GenerateSequenceNumberMessage generateSequenceNumberMessage);

    public void onIncomingGetUserIDMessage(GetUserIDMessage getUserIDMessage);

    public void onIncomingSequenceNumberMessage(SequenceNumberMessage sequenceNumberMessage);

    public void onIncomingUserIDMessage(UserIDMessage userIDMessage);

    public void onIncomingAliveMessage(AliveMessage aliveMessage);

    public void onIncomingElectionMessage(ElectionMessage electionMessage);

    public void onIncomingVictoryMessage(VictoryMessage victoryMessage);
}
