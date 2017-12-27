using System;
using System.Collections.Generic;
using System.Data.Entity.Migrations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Mayak.Migrations
{
    public sealed class Configuration : DbMigrationsConfiguration<MayakDbContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
        }

        protected override void Seed(MayakDbContext context)
        {
        }
    }
}
