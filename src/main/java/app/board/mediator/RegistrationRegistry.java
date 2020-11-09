package app.board.mediator;

import app.board.handlers.FrameSubscriber;
import app.board.handlers.SavePictureSubscriber;
import app.board.handlers.VideoSubscriber;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Contine handlerere pt evente.. ex primire poza de la o placa, nu contien date de user
 */
@Component("registrationRegistry")
public class RegistrationRegistry implements ApplicationContextAware {

    private Set<FrameSubscriber> frameSubscribers = Collections.synchronizedSet(new HashSet<>());

    private ApplicationContext applicationContext;

    //    @Override
    public void registerHomeFrameHandler(FrameSubscriber frameSubscriber) {
        if (frameSubscriber != null) {
            this.frameSubscribers.add(frameSubscriber);
        }
    }

    // TODO: create an iterator for each kind of subscriber
    // TODO: is returning an iterator bad practice?
    public Iterator<FrameSubscriber> getFrameSubscribersIterator() {
        return frameSubscribers.iterator();
    }

    public void unsubscribeFrameSubscriber(FrameSubscriber frameSubscriber) {
        if (frameSubscriber != null) {
            this.frameSubscribers.remove(frameSubscriber);
        }
    }

    @PostConstruct
    public void initBean() {
        frameSubscribers.add(applicationContext.getBean(VideoSubscriber.class));
        frameSubscribers.add(applicationContext.getBean(SavePictureSubscriber.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
