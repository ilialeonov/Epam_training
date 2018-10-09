/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.logic.TestimoniesLogic;
import by.epam.interpol.logic.PersonLogic;
import by.epam.interpol.logic.UserLogic;

/**
 *
 * @author Администратор
 */
public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand(new UserLogic());
        }
    }, 
    LOGOUT {
        {
            this.command = new LogoutCommand(new UserLogic());
        }
    }, 
    SHOW {
        {
            this.command = new ShowCommand(new PersonLogic());
        }
    },  
    CREATE {
        {
            this.command = new CreateCommand(new PersonLogic());
        }
    },
    REDIRECT {
        {
            this.command = new RedirectCommand();
        }
    },
    EDIT {
        {
            this.command = new EditCommand(new PersonLogic());
        }
    },
    MAIN {
        {
            this.command = new MainCommand(new PersonLogic());
        }
    }, 
    ARCHIVE {
        {
            this.command = new ArchiveCommand(new PersonLogic());
        }
    }, 
    TESTIMONYARCHIVE {
        {
            this.command = new TestimonyArchiveCommand(new TestimoniesLogic());
        }
    }, 
    REGION {
        {
            this.command = new RegionCommand(new PersonLogic());
        }
    },
    TESTRES {
        {
            this.command = new TestResultCommand();
        }
    },
    TESTIFY {
        {
            this.command = new TestifyCommand(new TestimoniesLogic());
        }
    },
    TESTIMONY {
        {
            this.command = new TestimoniesCommand(new TestimoniesLogic());
        }
    },
    FIND {
        {
            this.command = new FindCommand(new PersonLogic());
        }
    },
    FINDPROFIL {
        {
            this.command = new FindProfilCommand(new PersonLogic());
        }
    },
    REGISTER {
        {
            this.command = new RegisterCommand(new UserLogic());
        }
    },
    TEST {
        {
            this.command = new TestCommand();
        }
    };
    
    ActionCommand command;
    
    private CommandEnum() { 
        this.command = command;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
