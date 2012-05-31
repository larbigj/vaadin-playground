/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.larbig;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.vaadin.Application;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class ScriptRunnerApplication extends Application
{
    private Window window;

    @Override
    public void init()
    {
        window = new Window("Scriptrunner");
        setMainWindow(window);
        
        
        VerticalLayout layout = new VerticalLayout();
        final TextField tfDriver = new TextField("Driver");
        tfDriver.setWidth(300, Sizeable.UNITS_PIXELS);
        tfDriver.setValue("com.mysql.jdbc.Driver");
        final TextField tfURL = new TextField("URL");
        tfURL.setWidth(300, Sizeable.UNITS_PIXELS);
        tfURL.setValue("jdbc:mysql://mysql.host.net:3306/datenbank");
        final TextField tfUser = new TextField("User");
        tfUser.setWidth(300, Sizeable.UNITS_PIXELS);
        final TextField tfPasswort = new TextField("Password");
        tfPasswort.setWidth(300, Sizeable.UNITS_PIXELS);
        final TextArea taSQL = new TextArea("SQL");
        taSQL.setWidth(300, Sizeable.UNITS_PIXELS);
        taSQL.setHeight(150, Sizeable.UNITS_PIXELS);
        
        
        Button button = new Button("Execute");
        button.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

            	try {
            		Class.forName((String)tfDriver.getValue()).newInstance();
                    Connection con = DriverManager.getConnection( tfURL.getValue().toString(), tfUser.getValue().toString(), tfPasswort.getValue().toString() ); 
					ScriptRunner runner = new ScriptRunner(con, false, true);
					Reader r = new StringReader(taSQL.getValue().toString());
					runner.runScript(r);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            	catch (Exception e) {
					e.printStackTrace();
				}

            }
        });
        
        
        layout.addComponent(tfDriver);
        layout.addComponent(tfURL);
        layout.addComponent(tfUser);
        layout.addComponent(tfPasswort);
        layout.addComponent(taSQL);
        layout.addComponent(button);
        window.addComponent(layout);
        
    }
    
}
