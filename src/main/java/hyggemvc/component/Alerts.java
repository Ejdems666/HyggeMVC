package hyggemvc.component;

import javax.servlet.http.HttpSession;

/**
 * Created by adam on 28/02/2017.
 */
public class Alerts implements Component{
    private String alerts = "";
    private final HttpSession session;

    public Alerts(HttpSession session) {
        this.session = session;
        if (session.getAttribute("alerts") == null) {
            session.setAttribute("alerts",this);
        }
    }

    public void addAlert(Type type, String message) {
        alerts += "<div class='alert alert-"+type+"' role='alert'>"+message+"</div>";
    }

    @Override
    public String toString() {
        session.removeAttribute("alerts");
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
