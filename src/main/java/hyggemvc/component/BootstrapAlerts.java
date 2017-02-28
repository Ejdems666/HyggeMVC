package hyggemvc.component;

import javax.servlet.http.HttpSession;

/**
 * Created by adam on 28/02/2017.
 */
public class BootstrapAlerts implements Component{
    private String alerts = "";
    private final HttpSession session;

    public BootstrapAlerts(HttpSession session) {
        this.session = session;
        session.setAttribute("alerts",this);
    }

    public void addAlert(Type type, String message) {
        alerts += "<div class='alert alert-"+type+"' role='alert'>"+message+"</div>";
    }

    @Override
    public String toString() {
        session.setAttribute("alerts",null);
        return alerts;
    }

    public enum Type {
        ERROR("danger"),SUCCESS("success"),WARNING("warning"),INFO("info");

        private String type;
        Type(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }
}
