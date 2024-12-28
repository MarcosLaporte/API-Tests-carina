package web.automation.gui.fitnessPal.objects.account.enums;

public interface Options {
    int ordinal();

    default int getIndex() {
        return this.ordinal()+1;
    }
}
