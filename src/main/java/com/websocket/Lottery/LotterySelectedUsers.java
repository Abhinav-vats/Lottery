package com.websocket.Lottery;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LotterySelectedUsers {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    private final static Random rdm = new Random();

     public List<Users> populateUserTable(){
         List<Users> users = new ArrayList<>();

         for(int i=0; i<100; i++){
             Users user = new Users(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(5));
             users.add(user);
         }

         return userRepository.saveAll(users);

     }

     public List<Users> runLottery(){
         Optional<List<Users>> optionalUsers  = userRepository.findAllByIsActive(Boolean.TRUE);
         List<Users> selected  =new ArrayList<>();
         if(optionalUsers.isPresent()){
             List<BigInteger> ids = optionalUsers.get().stream().map(Users::getId).collect(Collectors.toList());
             for(int i = 0; i< 10; i++){
                 int ind = rdm.nextInt(ids.size());
                 Users user = optionalUsers.get().stream().filter(usr -> usr.getId().equals(ids.get(ind))).collect(Collectors.toList()).get(0);
                 user.setIsSelected(Boolean.TRUE);
                 selected.add(user);

             }
         }

         return userRepository.saveAll(selected);
     }


     @Scheduled(fixedRate = 5000)
    public void sendMessage() throws InterruptedException{
        Optional< List<Users> > selectedUsers = userRepository.findAllByIsSelected(Boolean.TRUE);
         List<Users> publishedUsers = new ArrayList<>();
         Map<String, Object> response = new LinkedHashMap<>();
        if(selectedUsers.isPresent()){
            Users users = null;
            for(Users user: selectedUsers.get()){
                if(!user.getIsPublished()){
                    users = user;
                    users.setIsPublished(Boolean.TRUE);
                    userRepository.save(users);
                    response.put("currentWinner", users);

                    break;
                }else{
                    if(publishedUsers.size()==selectedUsers.get().size()){
                        break;
                    }
                    publishedUsers.add(user);
                }
            }

            response.put("announced", publishedUsers);

            simpMessagingTemplate.convertAndSend("/topic/congo", response);
        }
     }

}
