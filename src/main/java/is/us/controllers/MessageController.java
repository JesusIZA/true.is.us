package is.us.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import is.us.domain.Message;
import is.us.domain.Views;
import is.us.repo.MessageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageRepository repository;

    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.IdNameCreate.class)
    public Message get(@PathVariable("id") Message message){
        return message;
    }

    @PostMapping
    public Message save(@RequestBody Message message){
        message.setCreationDate(LocalDateTime.now());
        return repository.save(message);
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFormDB, @RequestBody Message message){
        BeanUtils.copyProperties(message, messageFormDB, "id");
        return repository.save(messageFormDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message){
        repository.delete(message);
    }
}
