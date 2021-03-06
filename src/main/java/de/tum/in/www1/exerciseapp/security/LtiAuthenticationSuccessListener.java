package de.tum.in.www1.exerciseapp.security;

import de.tum.in.www1.exerciseapp.domain.Exercise;
import de.tum.in.www1.exerciseapp.service.LtiService;
import de.tum.in.www1.exerciseapp.web.rest.dto.LtiLaunchRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;


import javax.inject.Inject;

/**
 * Created by Josias Montag on 22/11/2016.
 */
@Component
@ComponentScan("de.tum.in.www1.exerciseapp.service.LtiService")
public class LtiAuthenticationSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private final Logger log = LoggerFactory.getLogger(LtiAuthenticationSuccessListener.class);

    @Inject
    private LtiService ltiService;



    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event)
    {

        /**
         * The InteractiveAuthenticationSuccessEvent is fired on manual logins and remember-me logins.
         * Not fired on programmatic logins!
         *
         */
        if (event instanceof InteractiveAuthenticationSuccessEvent)
        {

            AbstractAuthenticationToken token = (AbstractAuthenticationToken) event.getSource();
            WebAuthenticationDetails authDetails = (WebAuthenticationDetails) token.getDetails();
            String sessionId= authDetails.getSessionId();

            ltiService.handleLaunchRequestForSession(sessionId);

        }
    }



}
