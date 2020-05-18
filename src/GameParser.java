import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GameParser {
    public static void parseRound(int round) {
        GameBackend gb = GameBackend.getCurrent();
        BufferedReader bufferedReader = null;
        Monster buffer;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File("round\\round" + Integer.toString(round)) + ".txt"));
            while (bufferedReader.ready()) {
                String str = null;
                str = bufferedReader.readLine();
//                System.out.println(str);
                String data[] = str.split(" ");
//                System.out.println(data.length);
                Class[] arg = new Class[1];
                arg[0] = Monster.class;
                Method m = GameBackend.class.getMethod("add" + data[0], arg);
                m.invoke(gb, new Monster(Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), Game.MONSTER_INIT_WIDTH, Game.MONSTER_INIT_HEIGHT, Game.MONSTER_INIT_HEALTH, Game.MONSTER));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
