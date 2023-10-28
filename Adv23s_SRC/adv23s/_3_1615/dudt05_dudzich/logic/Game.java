package adv23s._3_1615.dudt05_dudzich.logic;

import adv23s._3_1615.dudt05_dudzich.api.*;

import java.util.Collection;
import java.util.Map;

public class Game implements IGame {
    /* Jediná existující instance hry. */
    private static final Game GAME = new Game();

    /* Vrátí jedinou existující instanci hry. */
    public static IGame getInstance()
    {
        return GAME;
    }
    /***************************************************************************
     * Vrátí informaci o tom, je-li hra aktuálně spuštěná.
     * Spuštěnou hru není možno pustit znovu.
     * Chceme-li hru spustit znovu, musíme ji nejprve ukončit.
     *
     * @return Je-li hra spuštěná, vrátí {@code true},
     *         jinak vrátí {@code false}
     */
    @Override
    public boolean isAlive()
    {
        return adv23s._3_1615.dudt05_dudzich.logic.Action.isAlive();
    }
    /***************************************************************************
     * Vrátí odkaz na batoh, do nějž bude hráč ukládat sebrané objekty.
     *
     * @return Batoh, do nějž hráč ukládá sebrané objekty
     */
    @Override
    public IBag bag()
    {
        return Bag.getInstance();
    }
    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře.
     *
     * @return Kolekce všech příkazů použitelných ve hře
     */
    @Override
    public Collection<adv23s._3_1615.dudt05_dudzich.logic.Action> allActions()
    {
        return adv23s._3_1615.dudt05_dudzich.logic.Action.allActions();
    }
    /***************************************************************************
     * Vrátí odkaz na přepravku s názvy povinných příkazů, tj. příkazů pro
     * <ul>
     *   <li>přesun hráče do jiného prostoru,</li>
     *   <li>zvednutí objektu (odebrání z prostoru a vložení do batohu),</li>
     *   <li>položení objektu (odebrání z batohu a vložení do prostoru),</li>
     *   <li>vyvolání nápovědy,</li>
     *   <li>okamžité ukončení hry.</li>
     * </ul>
     *
     * @return Přepravka s názvy povinných příkazů
     */
    @Override
    public BasicActions basicActions()
    {
        return new BasicActions("Go", "Take", "Put", "?", "Exit");
    }
    /***************************************************************************
     * Vrátí odkaz na mapu obsahující aktuální stav příznaků
     * ovlivňujících proveditelnost nestandardních akcí.
     *
     * @return Požadovaná mapa
     */
    @Override
    public Map<String, Object> conditions()
    {
//        Scenario happy = Scenarios.scenarios().get(0);
//        ScenarioStep start = happy.steps().get(0);
//        return start.sets;
        return adv23s._3_1615.dudt05_dudzich.logic.Action.CONDITIONS;
    }
    /***************************************************************************
     * Vrátí odkaz na mapu testů ověřujících splnění podmínek
     * nutných pro provedení nestandardních akcí.
     *
     * @return Požadovaná mapa
     */
    @Override
    public Map<String, IAction.ITest> tests()
    {
//        Scenario happy = Scenarios.scenarios().get(0);
//        ScenarioStep start = happy.steps().get(0);
//        List<String> tests = start.tests;
//        Map<String, IAction.ITest> result = new HashMap<>();
//        for (String name : tests) {
//            result.put(name, null);
//        }
//        return result;
        return adv23s._3_1615.dudt05_dudzich.logic.Action.TESTS;
    }
    /***************************************************************************
     * Vrátí odkaz na objekt reprezentující svět, v němž se hra odehrává.
     *
     * @return Svět, v němž se hra odehrává
     */
    @Override
    public IWorld world()
    {
        return World.getInstance();
    }
    /***************************************************************************
     * Zpracuje zadaný příkaz a vrátí text zprávy pro uživatele.
     *
     * @param command Zadávaný příkaz
     * @return Textová odpověď hry na zadaný příkaz
     */
    @Override
    public String executeCommand(String command)
    {
        return adv23s._3_1615.dudt05_dudzich.logic.Action.executeCommand(command);
    }
    /***************************************************************************
     * Ukončí celou hru a uvolní alokované prostředky.
     * Zadáním prázdného příkazu lze následně spustit hru znovu.
     */
    @Override
    public void stop()
    {
        Action.stop();
    }
}
