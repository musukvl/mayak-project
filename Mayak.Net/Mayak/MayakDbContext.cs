using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using Mayak.Model;

namespace Mayak
{
    public class MayakDbContext : DbContext
    {
        public DbSet<Location> Locations { get; set; }

        public MayakDbContext()
            : base("DefaultConnection")
        {
        }
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
         
            modelBuilder.Configurations.AddFromAssembly(Assembly.GetAssembly(GetType()));
        }
    }
}
