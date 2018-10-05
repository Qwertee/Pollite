using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace PolliteBackend.Models
{
    public class Poll
    {
        [Key]
        public int Id { get; set; }

        public string Prompt { get; set; }

        public string Hash { get; set; }

        public HashSet<Voter> Voters { get; set; }

        
    }
}