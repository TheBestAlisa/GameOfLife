import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Timer;

public class GUILife extends JFrame implements ListSelectionListener {

    private GamePanel gamePanel;
    private PatternStore store;
    private World world;
    private ArrayList<World> cachedWorlds;
    private JButton playButton;
    private java.util.Timer timer = new java.util.Timer();
    private boolean playing = false;


    public GUILife(PatternStore ps) {
        super("Game of Life");
        store = ps;
        cachedWorlds = new ArrayList<>();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLayout(new BorderLayout());

        add(createPatternsPanel(), BorderLayout.WEST);
        add(createControlPanel(), BorderLayout.SOUTH);
        add(createGamePanel(), BorderLayout.CENTER);
    }

    private World copyWorld(boolean useCloning) {
        World newest = null;
        if (!useCloning) {
            if (world instanceof ArrayWorld) {
                newest = new ArrayWorld((ArrayWorld) world);
            } else if (world instanceof PackedWorld) {
                newest = new PackedWorld((PackedWorld) world);
            }
        } else {
            newest = world.clone();
        }
        return newest;
    }

    private void moveBack() {

        if(world.getGenerationCount() == 0){
            System.out.println("First Generation!!!");
        }else if(world.getGenerationCount() > 0){
            world = cachedWorlds.get(world.getGenerationCount() - 1);
        }
        gamePanel.display(world);
    }

    private void moveForward() {

            if (world.getGenerationCount() + 1 >= cachedWorlds.size()) {
                        world.nextGeneration();
                        cachedWorlds.add(copyWorld(true));
                    } else {
                        world = cachedWorlds.get(world.getGenerationCount() + 1);
                    }
        gamePanel.display(this.world);
    }

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch, title);
        component.setBorder(tb);
    }

    private JPanel createGamePanel() {

        gamePanel = new GamePanel();
        addBorder(gamePanel, "Game Panel");
        try {
            this.cachedWorlds.add(new ArrayWorld(store.getPatterns()[0]));
            this.world =this.cachedWorlds.get(0);
            gamePanel.display(this.world);
        } catch (Exception ex) {

        }
        return gamePanel;
    }

    private JPanel createPatternsPanel() {
        JPanel patternsPanel = new JPanel();
        patternsPanel.setLayout(new BorderLayout());
        addBorder(patternsPanel, "Patterns");

        Pattern[] patterns = store.getPatterns();
        JList<Pattern> patternList = new JList<>(patterns);
        patternList.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(patternList);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        patternsPanel.add(scrollPane, BorderLayout.CENTER);
        return patternsPanel;
    }

    private JPanel createControlPanel() {
        JPanel ctrl = new JPanel();
        addBorder(ctrl, "Controls");
		ctrl.setLayout(new GridLayout(0, 3));

        JButton btnBack = new JButton("< Back");
        ctrl.add(btnBack, BorderLayout.SOUTH);
        btnBack.addActionListener(e -> moveBack());

        playButton = new JButton("Play");
        ctrl.add(playButton, BorderLayout.SOUTH);
        playButton.addActionListener(e -> runOrPause());

        JButton btnFrw = new JButton("Forward >");
        ctrl.add(btnFrw, BorderLayout.SOUTH);
        btnFrw.addActionListener(e -> moveForward());
        
        return ctrl;
    }

    public static void main(String[] args) throws Exception {
        PatternStore pat = new PatternStore("https://bit.ly/2FJERFh");
        GUILife gui = new GUILife(pat);
        gui.setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList<Pattern> list = (JList<Pattern>) e.getSource();
        Pattern p = list.getSelectedValue();
        try{
            if(p.getHeight() * p.getHeight() <= 64){
                    world = new PackedWorld(p);
            }else {
                    world = new ArrayWorld(p);
                }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
			world = null;
        }

        if (timer != null) {
            timer.cancel();
            playing = false;
            playButton.setText("Play");
            cachedWorlds.clear();
        }

        cachedWorlds.clear();
        gamePanel.display(world);
    }


    private void runOrPause() {
            if (playing) {
                timer.cancel();
                playing = false;
                playButton.setText("Play");
            } else {
                playing = true;
                playButton.setText("Stop");
                timer = new Timer(true);
                timer.scheduleAtFixedRate(new TimerTask() {

                    @Override
                    public void run() {
                        moveForward();
                    }
                }, 0, 500);
            }
    }
}