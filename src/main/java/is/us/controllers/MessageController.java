package is.us.controllers;

import is.us.exceptions.NotFoundException;
import is.us.model.Message;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private int count = 3;

    private List<Message> messages = new ArrayList<>(){{
        add(new Message("1", "text 1"));
        add(new Message("2", "message 2"));
    }};

    @GetMapping
    public List<Message> get(){
        return messages;
    }

    @GetMapping("{id}")
    public Message get(@PathVariable String id){
        return messages.stream()
                .filter(message -> message.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Message save(@RequestBody Message message){
        message.setId(String.valueOf(count++));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Message update(@PathVariable String id, @RequestBody Message message){
        var messageDB = messages.stream()
                .filter(mess -> mess.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);

        messageDB.setText(message.getText());
        return messageDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        var messageDB = messages.stream()
                .filter(mess -> mess.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);

        messages.remove(messageDB);
    }
}
