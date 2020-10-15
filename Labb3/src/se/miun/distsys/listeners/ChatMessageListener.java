package se.miun.distsys.listeners;

import se.miun.distsys.messages.*;

public interface ChatMessageListener {
    public void onIncomingChatMessage(ChatMessage chatMessage);

    public void onIncomingStatusMessage(StatusMessage statusMessage);

    public void onIncomingJoinMessage(JoinMessage joinMessage);

    public void onIncomingLeaveMessage(LeaveMessage leaveMessage);

    public void onIncomingAuthMessage(AuthMessage authMessage);

    public void onIncomingAssignMessage(AssignMessage assignMessage);

    public void onIncomingGenerateSequenceNumberMessage(GenerateSequenceNumberMessage generateSequenceNumberMessage);

    public void onIncomingGetUserIDMessage(GetUserIdMessage getUserIDMessage);

    public void onIncomingSequenceNumberMessage(SequenceNumberMessage sequenceNumberMessage);

    public void onIncomingUserIDMessage(UserIdMessage userIDMessage);

    public void onIncomingAliveMessage(AliveMessage aliveMessage);

    public void onIncomingElectionMessage(ElectionMessage electionMessage);

    public void onIncomingVictoryMessage(VictoryMessage victoryMessage);
}
