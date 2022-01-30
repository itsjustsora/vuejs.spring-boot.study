package com.taskagile.domain.appilcation;

import com.taskagile.domain.appilcation.commands.RegistrationCommand;
import com.taskagile.domain.model.user.RegistrationException;

public interface UserService {

    void register(RegistrationCommand command) throws RegistrationException;

}
