package se.miun.distsys.listeners;

import se.miun.distsys.messages.*;
import se.miun.distsys.messages.CoordinatorMessages.GenerateSequenceNumberMessage;
import se.miun.distsys.messages.CoordinatorMessages.GetUserIDMessage;
import se.miun.distsys.messages.CoordinatorMessages.SequenceNumberMessage;
import se.miun.distsys.messages.CoordinatorMessages.UserIDMessage;

public interface ChatMessageListener {
    public void onIncomingChatMessage(ChatMessage chatMessage);

    public void onIncomingStatusMessage(StatusMessage statusMessage);

    public void onIncomingJoinMessage(JoinMessage joinMessage);

    public void onIncomingLeaveMessage(LeaveMessage leaveMessage);

    public void onIncomingAuthMessage(AuthMessage authMessage);

    public void onIncomingAssignMessage(AssignMessage assignMessage);

    public void onIncomingGenerateSequenceNumberMessage(GenerateSequenceNumberMessage generateSequenceNumberMessage);

    public void onIncomingGetUserIDMessageMessage(GetUserIDMessage getUserIDMessage);

    public void onIncomingSequenceNumberMessage(SequenceNumberMessage sequenceNumberMessage);

    public void onIncomingUserIDMessage(UserIDMessage userIDMessage);
}
