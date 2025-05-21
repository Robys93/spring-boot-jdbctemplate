@Service
public class TutorialService {
    private final TutorialRepository tutorialRepository;

    public TutorialService(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    public List<Tutorial> findAll(String orderBy, Integer limit) {
        return tutorialRepository.findAll(orderBy, limit);
    }

    public Tutorial save(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    public int deleteById(long id) {
        return tutorialRepository.deleteById(id);
    }
}