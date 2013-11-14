package org.pablog.pills.security;

import java.util.Date;
import java.util.List;

import org.pablog.pills.domain.RememberMeToken;
import org.pablog.pills.repositories.RememberMeTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

public class MongoPersistentTokenRepositoryImpl implements PersistentTokenRepository {

	@Autowired RememberMeTokenRepository rememberMeTokenRepository;
    
     
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        RememberMeToken newToken = new RememberMeToken(token);
        this.rememberMeTokenRepository.save(newToken);
    }
 
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        RememberMeToken token = this.rememberMeTokenRepository.findBySeries(series);
        if (token != null){
            token.setTokenValue(tokenValue);
            token.setDate(lastUsed);
            this.rememberMeTokenRepository.save(token);
        }
 
    }
 
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
    	RememberMeToken token = this.rememberMeTokenRepository.findBySeries(seriesId);
    	// if for any reason the token has been removed from DB forces user to re-login
		if(token != null) {
    		return new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
    	} else {
    		return null;
    	}
    	   	
    }
 
    @Override
    public void removeUserTokens(String username) {
        List<RememberMeToken> tokens = this.rememberMeTokenRepository.findByUsername(username);
        this.rememberMeTokenRepository.delete(tokens);
    }

}
