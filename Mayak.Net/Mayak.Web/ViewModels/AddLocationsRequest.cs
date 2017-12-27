using System.Collections.Generic;

namespace Mayak.Web.ViewModels
{
    public class AddLocationsRequest
    {
        public IList<LocationViewModel> Locations { get; set; }
    }

    public class LocationViewModel
    {
        public long Id { get; set; }
        public double Lat { get; set; }
        public double Lon { get; set; }
        public long Date { get; set; }

    }
}
