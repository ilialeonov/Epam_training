/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command.util;

import by.epam.interpol.command.ArchiveCommand;
import by.epam.interpol.command.AssignCommand;
import by.epam.interpol.command.ChangeLanguageCommand;
import by.epam.interpol.command.CreateCommand;
import by.epam.interpol.command.DeleteCommand;
import by.epam.interpol.command.EditCommand;
import by.epam.interpol.command.FindCommand;
import by.epam.interpol.command.FindProfilCommand;
import by.epam.interpol.command.FoundCommand;
import by.epam.interpol.command.InfoCommand;
import by.epam.interpol.command.LoginCommand;
import by.epam.interpol.command.LogoutCommand;
import by.epam.interpol.command.MainCommand;
import by.epam.interpol.command.PurseCommand;
import by.epam.interpol.command.RedirectCommand;
import by.epam.interpol.command.RegionCommand;
import by.epam.interpol.command.RegisterCommand;
import by.epam.interpol.command.ShowCommand;
import by.epam.interpol.command.TestResultCommand;
import by.epam.interpol.command.TestifyCommand;
import by.epam.interpol.command.TestimoniesCommand;
import by.epam.interpol.command.TestimonyAdminArchiveCommand;
import by.epam.interpol.command.TestimonyArchiveCommand;
import by.epam.interpol.logic.TestimoniesLogic;
import by.epam.interpol.logic.PersonLogic;
import by.epam.interpol.logic.UserLogic;

/**
 *
 * @author Администратор
 */
public enum CommandEnum {
    ASSIGN {
        {
            this.command = new AssignCommand(new TestimoniesLogic());
        }
    },
    
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

    PURSE {
        {
            this.command = new PurseCommand(new UserLogic());
        }
    },

    SHOW {
        {
            this.command = new ShowCommand(new PersonLogic());
        }
    }, 

    CHANGELANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },

    CREATE {
        {
            this.command = new CreateCommand(new PersonLogic());
        }
    },

    DELETE {
        {
            this.command = new DeleteCommand(new PersonLogic());
        }
    },

    INFO {
        {
            this.command = new InfoCommand(new TestimoniesLogic());
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

    TESTIMONYADMINARCHIVE {
        {
            this.command = new TestimonyAdminArchiveCommand(new TestimoniesLogic());
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

    FOUND {
        {
            this.command = new FoundCommand(new PersonLogic());
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
    };
    
    ActionCommand command;
    
    private CommandEnum() { 
        this.command = command;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
