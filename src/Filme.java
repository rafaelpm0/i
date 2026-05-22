public class Filme {
    public float vote_average;
      public float vote_count;
       public float popularity;
        public float budget;
    public String title; 
    public float score; 


    // 🔧 Construtor
    public Filme( 
             float vote_average,
               float vote_count,
                  float popularity,
                float budget,
     String title
) {

        this.vote_average = vote_average;
        this.budget = budget;
        this.popularity = popularity;
     
        
        this.vote_count = vote_count;
           this.title = title;
           this.score = 0; // inicializa
     
    }

    
}