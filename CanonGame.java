import java.util.Scanner;
import java.util.Locale;
import java.util.Random;


public class CanonGame
{
  public static double m_vMax = 150;
  public static double m_gravite = 10;

  public static double m_zoneJoueur;
  public static double m_zoneIA;
  public static double m_nbTir;

  public static Scanner m_sc;
  public static Random m_rnd;

    public static void main(String[] args)
    {
        m_sc = new Scanner(System.in);
        m_rnd = new Random();

        
        DebutJeu();

        while(true)
        {
          if(TourJoueur())
          {
            break;
          }
          if(TourIa())
          {
            break;
          }
        }
    }


    private static void DebutJeu()
    {
      m_nbTir = 0;

      System.out.println("Saisir sa zone de stockage en donnant une distance comprise entre 500m et 1500m");
      m_zoneJoueur = m_sc.nextInt();
      m_zoneIA = m_rnd.nextInt(1500 - 500 + 1) + 500;
      System.out.println("L'IA a choisi comme zone : " + m_zoneIA);
    }

    private static boolean TourJoueur()
    {
      System.out.println("Saisir une puissance de feu entre 50% et 100%");
      double puissance = m_sc.nextInt();

      System.out.println("Saisir un angle de tire entre 25° et 75°");
      double alpha = m_sc.nextInt();

      double zoneDeTir = Tir(puissance, alpha);

      System.out.println("Vous avez tiré à : " + zoneDeTir);
      m_nbTir++;

      if (Touche(m_zoneIA, zoneDeTir))
      {
        System.out.println("Vous avez touché la cible, bravo !");
        System.out.println("Vous avez tirer " + m_nbTir + "fois");
        return true;
      }

      if(zoneDeTir > (m_zoneIA + 10))
      {
        System.out.println("Tir trop long");
      }

      if(zoneDeTir < (m_zoneIA - 10))
      {
        System.out.println("Tir trop court");
      }

      return false;
    }

    private static boolean TourIa()
    {
      double puissance = m_rnd.nextInt(100 - 50 + 1) + 50;
      double alpha = m_rnd.nextInt(75 - 25 + 1) + 25;
      double zoneDeTir = Tir(puissance, alpha);
      System.out.println("L'IA a tirée à : " + zoneDeTir); /*Pour voir si tout fonctionne*/
      
      if (Touche(m_zoneJoueur, zoneDeTir))
      {
        System.out.println("Vous avez perdu");
        return true;
      }
        return false;
      
    }

    private static double Tir(double _puissance, double _angle)
    {
       _angle = Math.sin(2*(_angle*(Math.PI/180)));

       return (Math.pow(m_vMax*(_puissance/100.0), 2.0)* _angle) / m_gravite;
    }

    private static boolean Touche(double _zoneCible, double _zoneDeTir)
    {
      return _zoneDeTir <= (_zoneCible + 10) && _zoneDeTir >= (_zoneCible - 10);
    }
}
