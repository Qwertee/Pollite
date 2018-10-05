namespace PolliteBackend.Models
{
    public class Voter
    {
        public int Id { get; set; }

        public string Hash { get; set; }

        public override bool Equals(object obj)
        {
            if (obj == null || !obj.GetType().Equals(this.GetType()))
            {
                return false;
            }
            else 
            {
                return ((Voter)obj).Hash.Equals(this.Hash);
            }
        }

        public override int GetHashCode()
        {
            return Hash.GetHashCode();
        }
    }
}