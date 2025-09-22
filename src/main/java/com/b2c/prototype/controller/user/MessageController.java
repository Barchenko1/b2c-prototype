package com.b2c.prototype.controller.user;

import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.MessageTemplateDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.processor.user.IMessageProcess;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/message")
public class MessageController {

    private final IMessageProcess messageProcess;

    public MessageController(IMessageProcess messageProcess) {
        this.messageProcess = messageProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveMessage(@RequestParam final Map<String, String> requestParams,
                                            @RequestBody final MessageTemplateDto messageTemplateDto) {
        messageProcess.saveMessage(requestParams, messageTemplateDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMessage(@RequestParam final Map<String, String> requestParams,
                                              @RequestBody final MessageDto messageDto) {
        messageProcess.updateMessage(requestParams, messageDto);
        return ResponseEntity.ok().build();
    }

//    @PutMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> changeMessageStatus(@RequestParam final Map<String, String> requestParams) {
//        messageProcess.changeMessageStatus(requestParams);
//        return ResponseEntity.ok().build();
//    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteMessage(@RequestParam final Map<String, String> requestParams) {
        messageProcess.deleteMessage(requestParams);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/clean", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> cleanUpMessagesByUserId(@RequestParam final Map<String, String> requestParams) {
        messageProcess.cleanUpMessagesByUserId(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/sender", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseMessageOverviewDto> getMessageOverviewBySenderEmail(@RequestParam final Map<String, String> requestParams) {
        return messageProcess.getMessageOverviewBySenderEmail(requestParams);
    }

    @GetMapping(value = "/receiver", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(@RequestParam final Map<String, String> requestParams) {
        return messageProcess.getMessageOverviewByReceiverEmail(requestParams);
    }

    @GetMapping(value = "/overview", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseMessageOverviewDto> getMessageOverviewListByUserId(@RequestParam final Map<String, String> requestParams) {
        return messageProcess.getMessageOverviewListByUserId(requestParams);
    }

    @GetMapping(value = "/payload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessagePayloadDto getMessagePayloadDto(@RequestParam final Map<String, String> requestParams) {
        return messageProcess.getMessagePayloadDto(requestParams);
    }

}
