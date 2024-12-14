package org.chatrullet.WaitingCollection;

import org.chatrullet.model.StatusEnum;
import org.chatrullet.model.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
@Component
public class UsersCollection {
    private final Queue<UserDTO> waitingQueue = new ConcurrentLinkedQueue<>();

    public boolean addUserInWaiting(UserDTO userDTO){
        return waitingQueue.offer(userDTO);
    }

    public synchronized UserDTO getNextUserAndDeleteUserFromQueueInId(UserDTO userDTO){
        removeUserFromQueue(userDTO);
        return waitingQueue.poll();
    }

    public UserDTO getNextUser(){
        UserDTO userDTO = waitingQueue.poll();
        while(!userDTO.getStatus().equals(StatusEnum.START)){
            userDTO = waitingQueue.poll();
        }
        userDTO.setStatus(StatusEnum.CHATTING);
        return userDTO;
    }

    public boolean isEmpty(){
        return waitingQueue.isEmpty();
    }

    public int getSizeCollection(){
        return waitingQueue.size();
    }

    public void removeUserFromQueue(UserDTO userDTO){
        waitingQueue.remove(userDTO);
    }


}
