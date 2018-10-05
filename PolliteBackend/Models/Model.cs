using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;

namespace PolliteBackend.Models
{
    public class PollContext : DbContext
    {
        public PollContext(DbContextOptions<PollContext> options) : base(options) {}

        public DbSet<Poll> Polls { get; set; }
        public DbSet<Voter> Voters { get; set; }
    }
}