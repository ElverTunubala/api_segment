import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import play.db.Database;
import repositories.SegmentRepository;
import repositories.CalzadaRepository;
import repositories.BordilloRepository;

public class Module extends AbstractModule {

    @Override
    public void configure() {
        // Bind repositories to their respective classes
        bind(SegmentRepository.class).asEagerSingleton();
        bind(CalzadaRepository.class).asEagerSingleton();
        bind(BordilloRepository.class).asEagerSingleton();
        
        // No necesitas hacer nada especial para Database, Play lo manejará automáticamente.
    }
}
