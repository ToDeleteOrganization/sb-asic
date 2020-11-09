package app.controller;

import app.board.BoardSubscribersHolder;
import arduino.board.BoardManager;
import arduino.command.ArduinoCommand;
import arduino.command.impl.DirectRemoteCommand;
import arduino.data.ArduinoMessage;
import arduino.manager.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Controller("/")
public class Esp32CamController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private BoardSubscribersHolder boardSubscribersHolder;

    //    private boolean isVideoActivated;

    ///////////////////
    @GetMapping("/esp/video")
    public String video(Model model)  {
        model.addAttribute("boards", boardSubscribersHolder.getBoardIds());
        return "espvideo";
    }



    //////////////////



    @GetMapping("/esp")
    @ResponseBody
    public String recordBoard(@RequestParam String boardId,
                              HttpServletRequest request) {
        log(request);
        System.out.println("************************************* module recorded");
        BoardManager.getBoardManagerInstance().addBoard(boardId, request.getRemoteAddr());
        return "Welcome " + boardId;
    }

    @GetMapping("/esp/boards")
    @ResponseBody
    public String showConnectedBoards(HttpServletRequest request) {
        log(request);
        return BoardManager.getBoardManagerInstance().getBoards().entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("; ", "{", "}"));
    }

    @GetMapping("/esp/redLed")
    @ResponseBody
    public void redLed(HttpServletRequest request) {
        log(request);
        boardService.executeCommand(new DirectRemoteCommand("red:on", null, null));
    }

    @GetMapping("/esp/greenLed")
    @ResponseBody
    public void greenLed(HttpServletRequest request) {
        log(request);
        final ArduinoCommand directRemoteCommand = new DirectRemoteCommand("green:on", null, null);
        directRemoteCommand.setNotifier((msg -> System.out.println("green led switched: [" + msg + "]")));

        boardService.executeCommand(directRemoteCommand);
    }

    private void writeToFile(byte[] content) {
        final String fileName = "C:/Users/malu/Desktop/esp32cam/conector/esp32cam_" + System.currentTimeMillis() + ".jpg";
        final FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(fileName));
            fos.write(content);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/esp/servo")
//    @ResponseBody
    public String servo() throws ExecutionException, InterruptedException, TimeoutException {
        final StringBuilder sb = new StringBuilder();
        final String boardIp = BoardManager.getBoardManagerInstance().getBoardIp("esp32CamBoard");
        if (boardIp != null) {

            int angle = 16;
            String command = "sv[" + angle + "]";
            final ArduinoCommand directRemoteCommand = new DirectRemoteCommand(command, boardIp, "/command");
            directRemoteCommand.setNotifier(msg -> {
                sb.append(((ArduinoMessage<Object>) msg).getContent());
                System.out.println(new String((byte[]) ((ArduinoMessage) msg).getContent()));
            });

            boardService.executeCommandWithResult(directRemoteCommand);
        }

        return sb.toString();
    }

    @GetMapping("/esp/photo")
//    @ResponseBody
    public void takePhoto(HttpServletRequest request, HttpServletResponse response) {
        log(request);
    }

//    @Scheduled(fixedRate = 500)
//    public void getPictureAndSendToSubscribers() throws ExecutionException, InterruptedException, TimeoutException {
//
//        if (isVideoActivated) {
//
//            final String boardIp = BoardManager.getBoardManagerInstance().getBoardIp("escp32CamBoard");
//            if (boardIp != null) {
//
//                final ArduinoCommand directRemoteCommand = new DirectRemoteCommand(null, boardIp, "/capture.jpg");
//                directRemoteCommand.setNotifier(msg -> {
//                    simpMessagingTemplate.convertAndSend("/camera/video", msg);
//                });
//
//                boardService.executeCommand(directRemoteCommand);
//            }
//        }
//    }

    private void writeToResponse(byte[] content, HttpServletResponse response) {
        try {
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(HttpServletRequest request) {
        System.out.println("Remote user: " + request.getRemoteUser());
        System.out.println("Remote host: " + request.getRemoteHost());
        System.out.println("Remote addr: " + request.getRemoteAddr());

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            final String headerValue = request.getHeader(headerName);

            System.out.println(headerName + ", " + headerValue);
        }
    }

}
