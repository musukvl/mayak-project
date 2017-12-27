using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Mayak.Model
{
    public class Location : IBaseEntity<long>
    {
        public long Id { get; set; }
        public double Lat { get; set; }
        public double Lon { get; set; }
        public long Date { get; set; }


    }
}
