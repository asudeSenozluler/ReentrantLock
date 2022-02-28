
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
//lock classı concurrentin altında
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;




public class ReentrantLockOrnegi {
    
  
    private int say = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    //Condition oluşturduktan sonra wait ve notify kullanabiliyorum ve conditionu locka bağlamış oluyorum
    
    public void artir(){
        for (int i = 0 ;  i < 10000 ; i++) {
            
            say++;
        }
        
        
    }
    public void thread1Fonksiyonu(){
        
        lock.lock();
        //anahtarı kilitlemiş olduk bunun altındaki yere başka thread giremiyor
        System.out.println("Thread 1 Çalışıyor...");
        System.out.println("Thread 1 Uyandırılmayı Bekliyor....");
        
        try {
            condition.await();
            //wait metodu ile aynı göreve sahip
        } catch (InterruptedException ex) {
            Logger.getLogger(ReentrantLockOrnegi.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Thread 1 Uyandırıldı ve İşlemine Devam Ediyor....");
        artir();

       
        
        lock.unlock();
        //bu işlemden sonra threadler artık işlemlere ulaşabiliyor
        
        
        
    }
    public void thread2Fonksiyonu(){
        try {
            Thread.sleep(1000);
            //thread2nin locku almadan önce beklenmesi isteniyor
        } catch (InterruptedException ex) {
            Logger.getLogger(ReentrantLockOrnegi.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scanner scanner = new Scanner(System.in);
        
        
        lock.lock();
        System.out.println("Thread 2 Çalışıyor....");
        
        artir();

        System.out.println("Devam etmek için bir tuşa basın...");
        scanner.nextLine();
        condition.signal();
        //notify metodu ile aynı göreve sahip
        System.out.println("Thread 1'i Uyandırdım.Ben gidiyorum...");
       
        lock.unlock();
        
        
        
        
        
    }
    public void threadOver(){
        System.out.println("Say değişkenin değeri : " + say);
        
        
    }
}
