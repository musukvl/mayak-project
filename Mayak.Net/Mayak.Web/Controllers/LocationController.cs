using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using Mayak.Model;
using Mayak.Web.ViewModels;

namespace Mayak.Web.Controllers
{
    [RoutePrefix("api/location")]
    public class LocationController : ApiController
    {
        [Route("add")]
        public async Task<IHttpActionResult> AddLocations(AddLocationsRequest request)
        {

            using (var ctx = new MayakDbContext())
            {
                foreach (var viewModel in request.Locations)
                {
                    var location = new Location
                    {
                        Date = viewModel.Date,
                        Lat = viewModel.Lat,
                        Lon = viewModel.Lon
                    };
                    ctx.Locations.Add(location);
                }
                await ctx.SaveChangesAsync();
            }
            return Ok();
        }  
    }
}
