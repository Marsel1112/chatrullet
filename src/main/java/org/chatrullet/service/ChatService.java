package org.chatrullet.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.chatrullet.WaitingCollection.UsersCollection;
import org.chatrullet.model.StatusEnum;
import org.chatrullet.model.UserDTO;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class ChatService {
    private final UsersCollection usersCollection;

    public UserDTO getUser(UserDTO DTO) throws InterruptedException {
        if (usersCollection.getSizeCollection() > 0) {
            UserDTO nextUser = usersCollection.getNextUser();
            if (nextUser != null) {
                DTO.setStatus(StatusEnum.CHATTING);
                usersCollection.notifyAll();
                return nextUser;
            } else {
                throw new RuntimeException("Пользователь не найден");
            }
        }

        addUserCollection(DTO);

        while(!DTO.getStatus().equals(StatusEnum.CHATTING)){
            DTO.wait(2000);
        }
        usersCollection.removeUserFromQueue(DTO);
        throw new RuntimeException("Нас нашли");
    }

    private void addUserCollection(UserDTO userDTO){
        usersCollection.addUserInWaiting(userDTO);
    }
}
